package com.mycompany.app.security.service;

import com.mycompany.app.dao.mysql.MysqlUserRepository;
import com.mycompany.app.entity.SysUser;
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

@Slf4j
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Resource
    MysqlUserRepository mysqlUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = mysqlUserRepository.getByAccountName(username);
        UserDetails user = null;
        if (sysUser != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (sysUser.getRoles() != null) {
                Arrays.stream(sysUser.getRoles().split(";")).forEach(t -> authorities.add(new SimpleGrantedAuthority(t)));
            }
            user = User.withUsername(sysUser.getAccountName()).password(sysUser.getPassword()).authorities(authorities).build();
        }
        return user;
    }
}
