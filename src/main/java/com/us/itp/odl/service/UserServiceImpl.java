package com.us.itp.odl.service;

import com.us.itp.odl.dao.UserRepository;
import com.us.itp.odl.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @NonNull private final UserRepository db;

    @Autowired
    public UserServiceImpl(UserRepository db) {
        this.db = db;
    }

    @Override
    @Nullable public User lookupUser(@NonNull final String username) {
        return db.getByUsername(username);
    }

    @Override
    @Transactional
    @NonNull public <T extends User> T saveUser(@NonNull final T user) {
        user.initializeUsername(() -> db.countUsersOfType(user.getClass()) + 1);
        return db.save(user);
    }
}
