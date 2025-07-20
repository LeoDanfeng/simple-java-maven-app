package com.mycompany.app.security.service;

import com.mycompany.app.dao.mysql.SysUserRepository;
import com.mycompany.app.entity.mysql.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* @Author: 罗丹枫
* @Description: 登录认证根据用户名从数据库获取用户信息
* @CreatedAt: 2024/7/11 22:23
*/

@Slf4j
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Resource
    SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserRepository.getByAccount(username);
        UserDetails user = null;
        if (sysUser != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (sysUser.getRoles() != null) {
                Arrays.stream(sysUser.getRoles().split(";")).forEach(t -> authorities.add(new SimpleGrantedAuthority(t)));
            }
            user = User.withUsername(sysUser.getAccount()).password(sysUser.getPassword()).authorities(authorities).build();
        }
        return user;
    }
}
