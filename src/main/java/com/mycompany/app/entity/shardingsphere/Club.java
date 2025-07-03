package com.mycompany.app.entity.shardingsphere;

import javax.persistence.*;

@Table(name = "club")
@Entity
public class Club {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String clubName;
    private String annualFee;
}
