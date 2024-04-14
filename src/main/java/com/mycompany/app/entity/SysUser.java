package com.mycompany.app.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_user")
public class SysUser {

    @Id
    @GeneratedValue
    private Long id;

    private String accountName;

    private String nickname;

    private String password;

    private String roles;
}
