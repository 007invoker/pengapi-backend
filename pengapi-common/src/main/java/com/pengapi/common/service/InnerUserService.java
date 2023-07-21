package com.pengapi.common.service;

import com.pengapi.common.model.entity.User;


/**
 * 用户服务
 *
 * @author yupi
 */
public interface InnerUserService {
    /**
     * 数据库中查是否已分配给用户秘钥（accessKey）
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);
}
