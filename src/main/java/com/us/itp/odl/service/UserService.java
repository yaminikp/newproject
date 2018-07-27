package com.us.itp.odl.service;

import com.us.itp.odl.dto.CustomerDto;
import com.us.itp.odl.model.User;
import org.springframework.lang.NonNull;

public interface UserService {
    User lookupUser(@NonNull String username);
    void createUser(@NonNull User user);
    void createUser(@NonNull CustomerDto customer);
}
