package com.mycompany.app.service;

import com.mycompany.app.dao.redis.RedisUserRepository;
import com.mycompany.app.entity.Person;
import com.mycompany.app.entity.redis.RedisPerson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
@Slf4j
public class RedisRepoTestService {

    @Resource
    RedisUserRepository redisUserRepository;

    @PostConstruct
    public void useRedisRepo() {
        save();
        Person person = get();

        log.info("person: {}", person);
    }

    private void save() {
        RedisPerson person = new RedisPerson();
        person.setId(100L);
        person.setFirstname("Alien");
        person.setLastname("Musk");
        redisUserRepository.save(person);
    }

    private Person get() {
        return redisUserRepository.findById(100L).orElse(null);
    }
}
