package com.sgzs.febs.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sgzs.febs.common.entity.system.SystemUser;

/**
 * @author: jianyufeng
 * @description: findByName方法用于通过用户名查找用户信息
 * @date: 2020/6/10 10:51
 */
public interface UserMapper extends BaseMapper<SystemUser> {
    SystemUser findByName(String username);
}
