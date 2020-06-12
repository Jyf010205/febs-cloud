package com.sgzs.febs.auth.manager;

import com.sgzs.febs.auth.mapper.MenuMapper;
import com.sgzs.febs.auth.mapper.UserMapper;
import com.sgzs.febs.common.entity.system.Menu;
import com.sgzs.febs.common.entity.system.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/10 10:56
 */
@Service
public class UserManager {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    public SystemUser findByName(String username){
        return userMapper.findByName(username);
    }

    public String findUserPermissions(String username){
        List<Menu> userPermissions = menuMapper.findUserPermissions(username);
        return userPermissions.stream().map(Menu::getPerms).collect(Collectors.joining(","));
    }
}
