package com.mycompany.app.dao;

import com.mycompany.app.entity.SysUser;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<SysUser,Long> {

    SysUser getByAccountName(String accountName);
}
