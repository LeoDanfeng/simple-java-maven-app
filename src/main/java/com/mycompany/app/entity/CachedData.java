package com.mycompany.app.entity;

import com.mycompany.app.common.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "cached_data")
public class CachedData extends BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String data;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

}
