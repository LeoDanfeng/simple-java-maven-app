package com.mycompany.app.dao.mysql;

import com.mycompany.app.entity.mysql.SysUser;
import org.springframework.data.repository.CrudRepository;

public interface MysqlUserRepository extends CrudRepository<SysUser,Long> {

    SysUser getByAccount(String accountName);
}
