package com.sgzs.febs.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sgzs.febs.common.entity.system.Menu;

import java.util.List;

/**
 * @author: jianyufeng
 * @description: findUserPermissions方法用于通过用户名查找用户权限集合
 * @date: 2020/6/10 10:52
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findUserPermissions(String username);
}
