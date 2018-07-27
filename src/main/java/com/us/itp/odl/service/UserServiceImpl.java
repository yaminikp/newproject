package com.us.itp.odl.service;

import com.us.itp.odl.dto.CustomerDto;
import com.us.itp.odl.model.Customer;
import com.us.itp.odl.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
