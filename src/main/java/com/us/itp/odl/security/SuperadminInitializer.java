package com.us.itp.odl.security;

import com.us.itp.odl.model.Superadmin;
import com.us.itp.odl.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public final class SuperadminInitializer implements ApplicationRunner {

    @NonNull private final UserService userService;

    @Nullable private final String username;
    @Nullable private final String password;

    @NonNull private final Logger logger = LoggerFactory.getLogger(this.getClass());

    SuperadminInitializer(
            @NonNull final UserService userService,
            @Nullable final String username,
            @Nullable final String password
    ) {
        this.userService = userService;
        this.username = username;
        this.password = password;
    }

    @Autowired
    public SuperadminInitializer(
            @NonNull final UserService userService,
            @NonNull final Environment env
    ) {
        this(userService,
                env.getProperty("odl.superadmin.username"),
                env.getProperty("odl.superadmin.password")
        );
    }

    @Override
    public void run(ApplicationArguments args) {
        run();
    }

    void run() {
        if (username == null || password == null) {
            logger.info("No superadmin registered; login details are missing or incomplete");
            return;
        }
        registerSuperadmin();
        logger.info("Superadmin registered under username {}", username);
    }

    private void registerSuperadmin() {
        userService.updateOrCreateUser(username,
                existingUser -> existingUser.setPassword(password),
                () -> new Superadmin(username, password)
        );
    }
}
