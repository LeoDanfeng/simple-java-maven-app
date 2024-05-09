package com.mycompany.app.dao.redis;

import com.mycompany.app.entity.redis.RedisPerson;
import org.springframework.data.repository.CrudRepository;

public interface RedisUserRepository extends CrudRepository<RedisPerson,Long> {

}
