package com.mycompany.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.app.common.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "address")
public class Address extends BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String city;

    private String province;

    private String detailAddress;

    @JsonIgnoreProperties(value = "addresses")
    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person person;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + super.getId() +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                '}';
    }
}
