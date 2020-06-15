package com.sgzs.febs.server.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.sgzs.febs.common.entity.FebsResponse;
import com.sgzs.febs.common.entity.QueryRequest;
import com.sgzs.febs.common.entity.system.SystemUser;
import com.sgzs.febs.common.exception.FebsException;
import com.sgzs.febs.common.utils.FebsUtil;
import com.sgzs.febs.server.system.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author: jianyufeng
 * @description: @Valid对应实体对象传参时的参数校验；
 *               @Validated对应普通参数的校验。
 * @date: 2020/6/13 15:34
 */
@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:view')")
    public FebsResponse userlist(QueryRequest queryRequest, SystemUser user){
        Map<String, Object> dataTable = FebsUtil.getDataTable(userService.findUserDetail(user, queryRequest));
        return new FebsResponse().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('user:add')")
    public void addUser(@Valid SystemUser user) throws FebsException {
        try {
            this.userService.createUser(user);
        }catch (Exception e){
            String message = "新增用户失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('user:update')")
    public void updateUser(@Valid SystemUser user) throws FebsException {
        try {
            this.userService.updateUser(user);
        }catch (Exception e){
            String message = "修改用户失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }

    @DeleteMapping("/{userIds}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) throws FebsException {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.userService.deleteUsers(ids);
        }catch (Exception e){
            String message = "删除用户失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
}
