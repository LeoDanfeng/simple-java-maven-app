package com.mycompany.app.entity.mysql;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_sharding")
public class Sharding {
    @Id
    @GeneratedValue(generator = "snowflake-id-generator")
    @GenericGenerator(
            name = "snowflake-id-generator",
            strategy = "com.mycompany.app.common.SnowFlakeIdGenerator"
    )
    private Long id;

    private String name;
}
