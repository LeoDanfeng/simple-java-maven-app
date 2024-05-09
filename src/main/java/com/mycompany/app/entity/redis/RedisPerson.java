package com.mycompany.app.entity.redis;

import com.mycompany.app.entity.Person;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("people")
@Data
public class RedisPerson extends Person {
    @Id
    private Long id;
}
