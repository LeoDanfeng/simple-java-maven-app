package com.mycompany.app.entity;

import com.mycompany.app.common.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String accountName;

    private String nickname;

    private String password;

    private String roles;
}
