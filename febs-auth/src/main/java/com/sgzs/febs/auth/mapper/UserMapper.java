package com.sgzs.febs.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sgzs.febs.common.entity.system.SystemUser;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/10 10:51
 */
public interface UserMapper extends BaseMapper<SystemUser> {
    SystemUser findByName(String username);
}
