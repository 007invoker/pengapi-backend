package com.peng.gateway;

import com.pengapi.common.model.entity.InterfaceInfo;
import com.pengapi.common.model.entity.User;
import com.pengapi.common.service.InnerInterfaceInfoService;
import com.pengapi.common.service.InnerUserInterfaceInfoService;
import com.pengapi.common.service.InnerUserService;
import com.pengapiclientsdk.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @DubboReference
    private InnerUserService innerUserService;
    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;
    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;
    private static final String INTERFACE_HOST = "http://localhost:8123";
//    private static final String INTERFACE_HOST = "http://175.178.181.126:8123";
    private static final List<String> IP_WHITE_LIST= Arrays.asList("127.0.0.1");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        1.用户发送请求到 API 网关
//        2.请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识:"+request.getId());
        String url = request.getPath().value();
        log.info("请求路径:"+url);
        String method = request.getMethod().toString();
        log.info("请求方法:"+method);
        log.info("请求参数:"+request.getQueryParams());
        String hostString = request.getRemoteAddress().getHostString();
        log.info("请求来源地址:"+hostString);
        ServerHttpResponse response = exchange.getResponse();
//        3.(黑白名单)
//        if(!IP_WHITE_LIST.contains(hostString)){
//            handleNoAuth(response);
//        }
//        4.用户鉴权 (判断 ak、sk 是否合法)
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");//时间戳
        String body = headers.getFirst("body");
        String sign = headers.getFirst("sign");//签名许可

        User invokeUser=null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);//从数据库中找这个用户的accessKey
        } catch (Exception e) {
            log.info("请求参数错误");
        }
        if(invokeUser==null){
            return handleNoAuth(response);
        }
        if(!accessKey.equals(invokeUser.getAccessKey())){
            throw new RuntimeException("无权限");
        }

        //时间不能大于5分钟
        long currentTime = System.currentTimeMillis() / 1000;
        long FIVE_MINEUTES=60*5L;
        if(currentTime - Long.parseLong(timestamp)>FIVE_MINEUTES ) {
            return handleNoAuth(response);
        }
        //随机数不能大于10000
        if(Long.parseLong(nonce)>10000){
            throw new RuntimeException("无权限");
        }
        //签证是否有效
        //TODO secretKey实际从数据库中查出
//        String secretKey="e0afad6a782a2de6b64f18e50515ae1a";
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtil.getSign(body,secretKey);
        if(!sign.equals(serverSign)){
            throw new RuntimeException("无权限");
        }
//        5.TODO 请求的模拟接口是否存在?  从数据库中查询,可以考虑用Feign、Dubbo、RestTemplate
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(INTERFACE_HOST+url, method);
            System.out.println("请求的url地址是: "+INTERFACE_HOST+url);
        } catch (Exception e) {
            log.error("getInterfaceInfo error", e);
        }
        if(interfaceInfo==null){
            return handleNoAuth(response);
        }
        return handleResponse(exchange,chain,interfaceInfo.getId(),invokeUser.getId());
    }
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,long interfaceInfoId, long userId){
        try {
            //从交换寄拿响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            //缓冲区工厂，拿到缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            //拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if(statusCode == HttpStatus.OK){
                //装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    //等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        //对象是响应式的
                        if (body instanceof Flux) {
                            //我们拿到真正的body
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            //往返回值里面写数据
                            //拼接字符串
                            return super.writeWith(
                                fluxBody.map(dataBuffer->{
                                    //7.调用成功 todo 接口调用次数 + 1 invokeCount
                                    try {
                                        innerUserInterfaceInfoService.invokeCount(interfaceInfoId,userId);
                                    } catch (Exception e) {
                                        log.error("invokeCount error");
                                    }

                                    byte[] content=new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(content);
                                    DataBufferUtils.release(dataBuffer);//释放内存
                                    //构建日志
                                    StringBuilder sb2 = new StringBuilder();
                                    List<Object> rspArgs = new ArrayList<>();
                                    rspArgs.add(originalResponse.getStatusCode());
                                    String data = new String(content, StandardCharsets.UTF_8);
                                    sb2.append(data);
                                    //打印日志
                                    log.info("响应结果"+data);
                                    return bufferFactory.wrap(content);
                                }));
                        } else {
                        //8.调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                //设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }
    private Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}