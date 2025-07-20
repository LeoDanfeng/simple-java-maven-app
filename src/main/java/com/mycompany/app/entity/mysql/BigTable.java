package com.mycompany.app.entity.mysql;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "big_table")
public class BigTable implements Serializable {

    private final static long serialVersionUID = 2025123456789L;

    @Id
    @GeneratedValue(generator = "snowflake-id-generator")
    @GenericGenerator(
            name = "snowflake-id-generator",
            strategy = "com.mycompany.app.common.SnowFlakeIdGenerator"
    )
    private Long id;

    private String name;
}
