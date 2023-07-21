package com.pengapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签证生成工具类
 * @body 用户参数
 * @secretKey 密匙
 * @return 不可解密的值
 */
public class SignUtil {
    public static String getSign(String body,String secretKey){
        Digester md5 = new Digester(DigestAlgorithm.SHA256);//用于生成签名的加密手段
        String content=body+'.'+secretKey;
        return md5.digestHex(content);
    }
}
