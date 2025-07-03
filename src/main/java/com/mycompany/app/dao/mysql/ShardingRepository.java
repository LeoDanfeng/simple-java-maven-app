package com.mycompany.app.dao.mysql;

import com.mycompany.app.entity.mysql.Sharding;
import org.springframework.data.repository.CrudRepository;

public interface ShardingRepository extends CrudRepository<Sharding,Long> {
}
