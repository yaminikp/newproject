package com.us.itp.odl.service;

import com.us.itp.odl.dao.UserRepository;
import com.us.itp.odl.dto.CustomerDto;
import com.us.itp.odl.model.Customer;
import com.us.itp.odl.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public final class UserServiceImpl implements UserService {

    @NonNull private UserRepository db;

    @Autowired
    UserServiceImpl(UserRepository db) {
        this.db = db;
    }

    @Override
    @Nullable public User lookupUser(@NonNull final String username) {
        return db.getByUsername(username);
    }

    @Override
    public void createUser(@NonNull final User user) {
        db.save(user);
    }

    @Override
    public void createUser(@NonNull final CustomerDto dto)  {
        createUser(new Customer(
                /* username = */ dto.getUsername(),
                /* password = */ dto.getPassword(),
                /* firstName = */ dto.getFirstName(),
                /* middleName = */ dto.getMiddleName(),
                /* lastName = */ dto.getLastName(),
                /* address = */ dto.getAddress(),
                /* email = */ dto.getEmail(),
                /* phoneNumber = */ dto.getPhoneNumber(),
               /* aadhaarCardNumber = */ dto.getAadhaarCardNumber()));
    }
}
