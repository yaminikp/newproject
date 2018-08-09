package com.us.itp.odl.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(@NonNull final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/customer").permitAll() // New customer registration
                .antMatchers("/superadmin/**").hasAuthority("ROLE_SUPERADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic();
    }
}
