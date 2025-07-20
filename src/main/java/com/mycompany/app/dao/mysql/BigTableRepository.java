package com.mycompany.app.dao.mysql;

import com.mycompany.app.entity.mysql.BigTable;
import org.springframework.data.repository.CrudRepository;

public interface BigTableRepository extends CrudRepository<BigTable,Long> {
}
