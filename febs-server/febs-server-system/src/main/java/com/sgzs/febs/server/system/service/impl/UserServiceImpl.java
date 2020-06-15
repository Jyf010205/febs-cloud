package com.sgzs.febs.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sgzs.febs.common.entity.QueryRequest;
import com.sgzs.febs.common.entity.system.SystemUser;
import com.sgzs.febs.common.entity.system.UserRole;
import com.sgzs.febs.server.system.mapper.UserMapper;
import com.sgzs.febs.server.system.service.IUserRoleService;
import com.sgzs.febs.server.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/12 18:01
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true,rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, SystemUser> implements IUserService {
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public IPage<SystemUser> findUserDetail(SystemUser user, QueryRequest request) {
        Page<SystemUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findUserDetailPage(page,user);
    }

    @Override
    @Transactional
    public void createUser(SystemUser user) {
        //创建用户
        user.setCreateTime(new Date());
        user.setAvatar(SystemUser.DEFAULT_AVATAR);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        //保存用户角色;
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);
    }

    @Override
    @Transactional
    public void updateUser(SystemUser user) {
        // 更新用户信息
        user.setPassword(null);
        user.setUsername(null);
        user.setCreateTime(null);
        user.setModifyTime(new Date());
        updateById(user);

        //更新用户角色
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,user.getUserId()));
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user,roles);
    }

    private void setUserRoles(SystemUser user, String[] roles) {
        Arrays.stream(roles).forEach(roleId->{
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(Long.valueOf(roleId));
            userRoleService.save(userRole);
        });
    }

    @Override
    public void deleteUsers(String[] userIds) {
        //删除用户信息
        List<String> list = Arrays.asList(userIds);
        removeByIds(list);
        //删除用户角色
        this.userRoleService.deleteUserRolesByRoleId(userIds);
    }
}
