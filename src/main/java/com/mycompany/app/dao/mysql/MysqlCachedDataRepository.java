package com.mycompany.app.dao.mysql;


import com.mycompany.app.entity.CachedData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MysqlCachedDataRepository extends CrudRepository<CachedData,Long> {

    List<CachedData> findAll();

}
