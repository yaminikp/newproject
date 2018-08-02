package com.us.itp.odl.controller;

import com.us.itp.odl.dto.CustomerDto;
import com.us.itp.odl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public final class UserController {

    @NonNull private final UserService userService;

    @Autowired
    public UserController(@NonNull final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@RequestBody @NonNull final CustomerDto dto) {
        userService.saveUser(dto.toCustomer());
    }
}
