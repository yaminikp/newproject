package com.us.itp.odl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@Entity
@Inheritance
public abstract class User {

    @Id @GeneratedValue private long id;

    @Column(unique = true) @NonNull private String username;

    public User(@NonNull final String username) {
        this.username = username;
    }
}
