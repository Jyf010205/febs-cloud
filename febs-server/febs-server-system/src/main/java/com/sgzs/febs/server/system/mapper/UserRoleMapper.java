package com.sgzs.febs.server.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sgzs.febs.common.entity.system.UserRole;
import feign.Param;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/6/13 15:04
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户id删除该用户的角色关系
     * @param userId
     * @return
     */
    Boolean deleteByUserId(@Param("userId") Long userId);

    /**
     * 根据角色Id删除该角色的用户关系
     * @param roleId
     * @return
     */
    Boolean deleteByRoleId(@Param("roleId") Long roleId);
}
