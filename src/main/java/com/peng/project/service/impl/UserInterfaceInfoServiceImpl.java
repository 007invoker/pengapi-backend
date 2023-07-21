package com.peng.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peng.project.common.ErrorCode;
import com.peng.project.exception.BusinessException;
import com.peng.project.mapper.UserInterfaceInfoMapper;
import com.peng.project.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.peng.project.service.UserInterfaceInfoService;
import com.pengapi.common.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

/**
* @author invoker
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2023-03-30 22:38:08
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userinterfaceInfo, boolean b) {
        if (userinterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 创建时，所有参数必须非空
        if (b) {
            if (userinterfaceInfo.getInterfaceInfoId()<=0 || userinterfaceInfo.getUserId()<=0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口禁用或用户不存在");
            }
        }
        if (userinterfaceInfo.getLeftNum()<0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }
    }

    /**
     * 调用次数+1,剩余次数-1
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        if(interfaceInfoId<=0||userId<=0) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        synchronized (this) {
            updateWrapper.eq("interfaceInfoId", interfaceInfoId);
            updateWrapper.eq("userId", userId);
            updateWrapper.setSql("leftNum=leftNum-1,totalNum=totalNum+1");
        }
        return this.update(updateWrapper);
    }


}




