package com.us.itp.odl.model;

import static com.us.itp.odl.security.AuthorityUtil.extendAuthoritySet;

import java.util.Collection;
import java.util.Set;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

@Entity
@NoArgsConstructor
public class Superadmin extends User {

    public static final Set<GrantedAuthority> AUTHORITIES =
            extendAuthoritySet(User.AUTHORITIES, "ROLE_SUPERADMIN");

    public Superadmin(@NonNull final String username, @NonNull final String password) {
        super(username, password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AUTHORITIES;
    }
}
