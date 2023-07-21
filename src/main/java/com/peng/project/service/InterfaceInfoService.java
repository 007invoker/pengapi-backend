package com.peng.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pengapi.common.model.entity.InterfaceInfo;

/**
* @author invoker
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-03-25 18:27:35
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 校验
     * @param interfaceInfo
     * @param add
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
