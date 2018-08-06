package com.us.itp.odl.controller;

import com.us.itp.odl.dto.CustomerDto;
import com.us.itp.odl.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public final class CustomerController {

    @NonNull private final UserService userService;

    @Autowired
    public CustomerController(@NonNull final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/customer")
    public ResponseEntity<Void> createCustomer(
            @RequestBody @NonNull @Valid final CustomerDto dto,
            @NonNull final BindingResult binding
    ) {
        if (binding.hasErrors()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        userService.saveUser(dto.toCustomer());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
