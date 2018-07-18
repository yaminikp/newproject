package com.us.itp.odl.service;

import com.us.itp.odl.model.User;
import java.util.HashMap;
import java.util.Map;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public final class UserServiceImpl implements UserService {

    @NonNull private final Map<String, User> db = new HashMap<>();

    @Override
    @Nullable public User lookupUser(@NonNull final String username) {
        return db.get(username);
    }

    @Override
    public void createUser(@NonNull final User user) {
        db.put(user.getUsername(), user);
    }
}
