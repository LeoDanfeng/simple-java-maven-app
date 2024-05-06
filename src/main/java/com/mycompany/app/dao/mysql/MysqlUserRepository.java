package com.mycompany.app.dao.mysql;

import com.mycompany.app.entity.SysUser;
import org.springframework.data.repository.CrudRepository;

public interface MysqlUserRepository extends CrudRepository<SysUser,Long> {

    SysUser getByAccountName(String accountName);
}
