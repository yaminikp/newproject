package com.us.itp.odl.model;

import javax.persistence.Entity;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Entity
@NoArgsConstructor
public class Superadmin extends User {

    public Superadmin(@NonNull final String username, @NonNull final String password) {
        super(username, password);
    }
}
