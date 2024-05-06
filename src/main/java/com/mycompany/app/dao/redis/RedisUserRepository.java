package com.mycompany.app.dao.redis;

import com.mycompany.app.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface RedisUserRepository extends CrudRepository<Person,Long> {

}
