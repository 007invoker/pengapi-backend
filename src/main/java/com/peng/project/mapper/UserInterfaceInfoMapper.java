package com.peng.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pengapi.common.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author invoker
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Mapper
* @createDate 2023-03-30 22:38:08
* @Entity generator.domain.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    /**
     * 统计调用前三的接口
     * @param limit
     * @return
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(long limit);
}




