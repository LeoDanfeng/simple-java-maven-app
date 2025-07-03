package com.mycompany.app.controller;

import com.mycompany.app.dao.mysql.ReadWriteSplitRepository;
import com.mycompany.app.dao.mysql.ShardingRepository;
import com.mycompany.app.dao.redis.RequestStatisticRepository;
import com.mycompany.app.entity.mysql.ReadWriteSplit;
import com.mycompany.app.entity.mysql.Sharding;
import com.mycompany.app.entity.redis.RequestStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private ReadWriteSplitRepository readWriteSplitRepository;

    @Autowired
    private ShardingRepository shardingRepository;

    @Autowired
    private RequestStatisticRepository requestStatisticRepository;

    @GetMapping("currentUser")
    public String currentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("sharding/insert")
    public Object sharding(@RequestBody Sharding sharding) {
        return shardingRepository.save(sharding);
    }

    @GetMapping("sharding/getAll")
    public Object getSharding() {
        return shardingRepository.findAll();
    }

    @PostMapping("split/insert")
    public Object split(@RequestBody ReadWriteSplit split) {
        return readWriteSplitRepository.save(split);
    }

    @GetMapping("split/getAll")
    public Object getSplit() {
        return readWriteSplitRepository.findAll();
    }
    @GetMapping("redis/getAll")
    public Object getRedis() {
        return requestStatisticRepository.findAll();
    }

    @PostMapping("redis/getBy")
    public Object getRedis(@RequestBody RequestStatistic requestStatistic) {
        return requestStatisticRepository.getByAccessTimeGreaterThan(requestStatistic.getAccessTime());
    }

}
