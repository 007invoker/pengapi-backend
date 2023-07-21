package com.peng.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.peng.project.annotation.AuthCheck;
import com.peng.project.common.BaseResponse;
import com.peng.project.common.ErrorCode;
import com.peng.project.common.ResultUtils;
import com.peng.project.exception.BusinessException;
import com.peng.project.mapper.UserInterfaceInfoMapper;
import com.peng.project.model.vo.InterfaceInfoVO;
import com.peng.project.service.InterfaceInfoService;
import com.pengapi.common.model.entity.InterfaceInfo;
import com.pengapi.common.model.entity.UserInterfaceInfo;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分析控制器
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfoList= userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream().
                collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));

        QueryWrapper wrapper=new QueryWrapper();
        wrapper.in("id",interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(wrapper);

        if(CollectionUtils.isEmpty(interfaceInfoList)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<InterfaceInfoVO> collect = interfaceInfoList.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO=new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo,interfaceInfoVO);
            Integer totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(collect);
    }
}
