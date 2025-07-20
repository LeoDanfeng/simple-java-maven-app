package com.mycompany.app.entity.mysql;

import com.mycompany.app.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "sys_user")
public class SysUser extends BaseEntity implements Serializable {

    private final static long serialVersionUID = 2025123456789L;

    private String account;

    private String nickname;

    private String password;

    private String roles;
}
