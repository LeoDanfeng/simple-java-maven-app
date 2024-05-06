package com.mycompany.app.entity;

import com.mycompany.app.common.BaseEntity;
import lombok.Data;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "person")
public class Person extends BaseEntity implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Indexed
    private String firstname;
    private String lastname;

    @OneToMany(mappedBy = "person")
    private List<Address> addresses;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + super.getId() +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
