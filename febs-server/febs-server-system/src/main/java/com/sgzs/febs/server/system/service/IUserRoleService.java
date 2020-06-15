package com.sgzs.febs.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sgzs.febs.common.entity.system.UserRole;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/13 15:09
 */
public interface IUserRoleService extends IService<UserRole> {

    void deleteUserRoleByUserId(String[] roleIds);

    void deleteUserRolesByRoleId(String[] userIds);
}
