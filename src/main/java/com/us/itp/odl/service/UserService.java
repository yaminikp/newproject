package com.us.itp.odl.service;

import com.us.itp.odl.model.User;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    @Nullable User lookupUser(@NonNull String username);
    void createUser(@NonNull User user);

    @Override
    @NonNull default UserDetails loadUserByUsername(@NonNull final String username)
    throws UsernameNotFoundException {
        final UserDetails user = lookupUser(username);
        if (user == null) throw new UsernameNotFoundException(
                "Cannot locate user with username '" + username + "'"
        );
        return user;
    }
}
