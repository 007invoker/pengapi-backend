package com.peng.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.peng.project.annotation.AuthCheck;
import com.peng.project.common.*;
import com.peng.project.constant.CommonConstant;
import com.peng.project.constant.UserConstant;
import com.peng.project.exception.BusinessException;
import com.peng.project.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.peng.project.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.peng.project.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest;
import com.peng.project.service.UserInterfaceInfoService;
import com.peng.project.service.UserService;
import com.pengapi.common.model.entity.User;
import com.pengapi.common.model.entity.UserInterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 接口控制
 *
 * @author yupi
 */
@RestController
@RequestMapping("/userinterfaceInfo")
@Slf4j
public class UserInterfaceInfoController {

    @Resource
    private UserInterfaceInfoService userinterfaceInfoService;
    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param userinterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUserInterfaceInfo(@RequestBody UserInterfaceInfoAddRequest userinterfaceInfoAddRequest, HttpServletRequest request) {
        if (userinterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userinterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userinterfaceInfoAddRequest, userinterfaceInfo);
        // 校验
        userinterfaceInfoService.validUserInterfaceInfo(userinterfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        userinterfaceInfo.setUserId(loginUser.getId());
        boolean result = userinterfaceInfoService.save(userinterfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newUserInterfaceInfoId = userinterfaceInfo.getId();
        return ResultUtils.success(newUserInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUserInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userinterfaceInfoService.getById(id);
        if (oldUserInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldUserInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = userinterfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param userinterfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUserInterfaceInfo(@RequestBody UserInterfaceInfoUpdateRequest userinterfaceInfoUpdateRequest,
                                            HttpServletRequest request) {
        if (userinterfaceInfoUpdateRequest == null || userinterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo UserInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userinterfaceInfoUpdateRequest, UserInterfaceInfo);
        // 参数校验
        userinterfaceInfoService.validUserInterfaceInfo(UserInterfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = userinterfaceInfoUpdateRequest.getId();
        // 判断是否存在
        UserInterfaceInfo oldUserInterfaceInfo = userinterfaceInfoService.getById(id);
        if (oldUserInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldUserInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = userinterfaceInfoService.updateById(UserInterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<UserInterfaceInfo> getUserInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo UserInterfaceInfo = userinterfaceInfoService.getById(id);
        return ResultUtils.success(UserInterfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param userinterfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<UserInterfaceInfo>> listUserInterfaceInfo(UserInterfaceInfoQueryRequest userinterfaceInfoQueryRequest) {
        UserInterfaceInfo UserInterfaceInfoQuery = new UserInterfaceInfo();
        if (userinterfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(userinterfaceInfoQueryRequest, UserInterfaceInfoQuery);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(UserInterfaceInfoQuery);
        List<UserInterfaceInfo> UserInterfaceInfoList = userinterfaceInfoService.list(queryWrapper);
        return ResultUtils.success(UserInterfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param userinterfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<UserInterfaceInfo>> listUserInterfaceInfoByPage(UserInterfaceInfoQueryRequest userinterfaceInfoQueryRequest, HttpServletRequest request) {
        if (userinterfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userinterfaceInfoQuery = new UserInterfaceInfo();
        BeanUtils.copyProperties(userinterfaceInfoQueryRequest, userinterfaceInfoQuery);
        long current = userinterfaceInfoQueryRequest.getCurrent();
        long size = userinterfaceInfoQueryRequest.getPageSize();
        String sortField = userinterfaceInfoQueryRequest.getSortField();
        String sortOrder = userinterfaceInfoQueryRequest.getSortOrder();
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>(userinterfaceInfoQuery);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<UserInterfaceInfo> UserInterfaceInfoPage = userinterfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(UserInterfaceInfoPage);
    }

    // endregion
}
