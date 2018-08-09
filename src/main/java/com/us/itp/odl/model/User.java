package com.us.itp.odl.model;

import static com.us.itp.odl.security.AuthorityUtil.authoritySet;

import java.util.Collection;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@Entity
@Inheritance
@ToString(exclude = "password")
public abstract class User implements OdlEntity, UserDetails {

    public static final Set<GrantedAuthority> AUTHORITIES = authoritySet("USER");

    @Id @GeneratedValue private long id;

    @Column(unique = true) @NonNull private String username;
    @NonNull private String password;

    public User(@NonNull final String username, @NonNull final String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    @NonNull public Collection<? extends GrantedAuthority> getAuthorities() {
        return AUTHORITIES;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
