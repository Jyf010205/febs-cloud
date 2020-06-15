package com.sgzs.febs.server.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sgzs.febs.common.entity.system.UserRole;
import com.sgzs.febs.server.system.mapper.UserRoleMapper;
import com.sgzs.febs.server.system.service.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/13 15:23
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    @Transactional
    public void deleteUserRoleByUserId(String[] roleIds) {
        Arrays.stream(roleIds).forEach(roleId->{
            baseMapper.deleteByUserId(Long.valueOf(roleId));
        });
    }

    @Override
    @Transactional
    public void deleteUserRolesByRoleId(String[] userIds) {
        Arrays.stream(userIds).forEach(userId->{
            baseMapper.deleteByRoleId(Long.valueOf(userId));
        });
    }
}
