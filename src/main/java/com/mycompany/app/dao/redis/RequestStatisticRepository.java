package com.mycompany.app.dao.redis;

import java.util.List;

import com.mycompany.app.entity.redis.RequestStatistic;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface RequestStatisticRepository extends CrudRepository<RequestStatistic, String> {

    RequestStatistic getByIdOrAccessUriOrAccessUser(String id, String accessUri, String accessUser);

}
