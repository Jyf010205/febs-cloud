package com.sgzs.febs.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sgzs.febs.common.entity.system.Menu;

import java.util.List;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/10 10:52
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findUserPermissions(String username);
}
