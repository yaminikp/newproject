package com.us.itp.odl.model;

import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
public final class TestUser extends User {

    @NonNull private String rawPassword;

    public TestUser(@NonNull final String username, @NonNull final String rawPassword) {
        super(username, encodePassword(rawPassword));
        this.rawPassword = rawPassword;
    }

    @NonNull private static String encodePassword(@NonNull final String rawPassword) {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(rawPassword);
    }

    public TestUser(@NonNull final String username) {
        this(username, "arbitrary password");
    }
}
