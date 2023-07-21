package com.peng.project.service.innerimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.peng.project.common.ErrorCode;
import com.peng.project.exception.BusinessException;
import com.peng.project.service.UserService;
import com.pengapi.common.model.entity.User;
import com.pengapi.common.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
    public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserService userService;
    @Override
    public User getInvokeUser(String accessKey) {
        if(StringUtils.isAnyBlank(accessKey)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("accessKey",accessKey);
        User user = userService.getOne(query);
        return user;
    }
}
