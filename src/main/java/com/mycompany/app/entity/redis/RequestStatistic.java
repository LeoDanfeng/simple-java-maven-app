package com.mycompany.app.entity.redis;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@RedisHash("requestStatistic")
@Data
public class RequestStatistic {
    @Id
    private String id;
    @Indexed
    private String accessUser;
    @Indexed
    private String accessUri;
    @DateTimeFormat
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private LocalDateTime accessTime;
    private String accessIp;
    private String remoteAddr;
    private String args;
}
