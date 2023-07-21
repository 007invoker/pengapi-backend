package com.peng.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pengapi.common.model.entity.UserInterfaceInfo;

/**
* @author invoker
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
* @createDate 2023-03-30 22:38:08
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 校验数据
     * @param userinterfaceInfo
     * @param b
     */
    void validUserInterfaceInfo(UserInterfaceInfo userinterfaceInfo, boolean b);

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId,long userId);

}
