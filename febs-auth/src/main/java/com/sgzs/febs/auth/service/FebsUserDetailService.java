package com.sgzs.febs.auth.service;

import com.sgzs.febs.auth.entity.FebsAuthUser;
import com.sgzs.febs.auth.manager.UserManager;
import com.sgzs.febs.common.entity.system.SystemUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author: jianyufeng
 * @description:模拟数据库返回的信息
 * @date: 2020/5/28 17:07
 */
@Service
public class FebsUserDetailService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserManager userManager;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = userManager.findByName(username);
        if (systemUser != null){
            String permissions = userManager.findUserPermissions(username);
            boolean notLocked = false;
            if (StringUtils.equals(SystemUser.STATUS_VALID,systemUser.getStatus())) {
                notLocked = true;
            }
            FebsAuthUser authUser = new FebsAuthUser(systemUser.getUsername(), systemUser.getPassword(), true, true, true, notLocked, AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
            BeanUtils.copyProperties(systemUser,authUser);
            return authUser;
        }else {
            throw new UsernameNotFoundException("");
        }
    }
}
