package com.peng.project.service.innerimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.peng.project.common.ErrorCode;
import com.peng.project.exception.BusinessException;
import com.peng.project.service.InterfaceInfoService;
import com.pengapi.common.model.entity.InterfaceInfo;
import com.pengapi.common.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoService interfaceInfoService;
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("method", method).eq("url", url);
        return interfaceInfoService.getOne(wrapper);
    }
}
