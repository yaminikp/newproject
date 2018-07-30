package com.us.itp.odl.controller;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public final class LoginController {

    /** Scaffolding endpoint to verify that authentication is in place. */
    // TODO: Remove this once we have another way to verify authentication.
    @GetMapping("/login-test")
    @NonNull public String loginTest() {
        return "login-test";
    }
}
