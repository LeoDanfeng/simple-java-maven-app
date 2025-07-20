package com.mycompany.app.controller;

import com.mycompany.app.config.DynamicProperties;
import com.mycompany.app.dao.mysql.NormalTableRepository;
import com.mycompany.app.dao.mysql.BigTableRepository;
import com.mycompany.app.dao.redis.RequestStatisticRepository;
import com.mycompany.app.entity.mysql.NormalTable;
import com.mycompany.app.entity.mysql.BigTable;
import com.mycompany.app.entity.redis.RequestStatistic;
import com.mycompany.app.mail.MailUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@AllArgsConstructor
@RestController
@RequestMapping("test")
public class TestController {

    private final DynamicProperties dynamicProperties;

    private final NormalTableRepository normalTableRepository;

    private final BigTableRepository bigTableRepository;

    private final RequestStatisticRepository requestStatisticRepository;

    private final MailUtil mailUtil;


    @GetMapping("currentUser")
    public String currentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("big-table/insert")
    public Object sharding(@RequestBody BigTable bigTable) {
        return bigTableRepository.save(bigTable);
    }

    @GetMapping("big-table/getAll")
    public Object getSharding() {
        return bigTableRepository.findAll();
    }

    @PostMapping("normal-table/insert")
    public Object split(@RequestBody NormalTable normalTable) {
        return normalTableRepository.save(normalTable);
    }

    @GetMapping("normal-table/getAll")
    public Object getSplit() {
        return normalTableRepository.findAll();
    }

    @GetMapping("redis/getAll")
    public Object getRedis() {
        return requestStatisticRepository.findAll();
    }

    @PostMapping("redis/getBy")
    public Object getRedis(@RequestBody RequestStatistic requestStatistic) {
        return requestStatisticRepository.getByIdOrAccessUriOrAccessUser(requestStatistic.getId(), requestStatistic.getAccessUri(), requestStatistic.getAccessUser());
    }

    @GetMapping("dynamic-properties")
    public Object getDynamicProperties() {
        return dynamicProperties.getExampleKey();
    }

    @PostMapping("sendEmail")
    public String sendEmail() {
        mailUtil.sendSimpleEmail("1098506605@qq.com", "test", "test");
        return "Send email successfully.";
    }
}
