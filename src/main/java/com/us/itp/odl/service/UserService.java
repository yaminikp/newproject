package com.us.itp.odl.service;

import static com.us.itp.odl.util.FunctionUtil.apply;

import com.us.itp.odl.model.User;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    @Nullable User lookupUser(@NonNull String username);

    /**
     * Persists the given {@code User}.
     *
     * @return a possibly different instance of the saved {@code User} that reflects any changes
     *   made as part of the persistence operation (such as auto-generation of ID and username in
     *   the case of a freshly-created {@code User}).
     */
    @NonNull <T extends User> T saveUser(@NonNull T user);

    default boolean userExists(@NonNull final String username) {
        return lookupUser(username) != null;
    }

    default void updateOrCreateUser(
            @NonNull final String username,
            @NonNull final Consumer<User> updater,
            @NonNull final Supplier<User> creator
    ) {
        final User user = Optional.ofNullable(lookupUser(username))
                .map(apply(updater))
                .orElse(creator.get());
        saveUser(user);
    }

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
