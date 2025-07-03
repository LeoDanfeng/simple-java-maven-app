package com.mycompany.app.entity.mysql;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_read_write_split")
public class ReadWriteSplit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
