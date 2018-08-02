package com.us.itp.odl.security;

import static org.junit.Assert.assertEquals;

import com.us.itp.odl.dao.FakeUserRepository;
import com.us.itp.odl.dao.UserRepository;
import com.us.itp.odl.service.UserService;
import com.us.itp.odl.service.UserServiceImpl;
import java.util.UUID;
import org.junit.After;
import org.junit.Test;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;

public final class SuperadminInitializerTests {

    @NonNull private final UserRepository repo = new FakeUserRepository();
    @NonNull private final UserService userService = new UserServiceImpl(repo);

    private String username = UUID.randomUUID().toString();
    private String password = UUID.randomUUID().toString();

    @After
    public void resetDatabase() {
        repo.deleteAll();
    }

    @Test
    public void superadminIsRegistered() {
        runInitializer(username, password);
        assertSuperadminPasswordIs(password);
    }

    @Test
    public void nullSuperadminIsNotRegistered() {
        runInitializer(/* username = */ null, /* password = */ null);
        runInitializer(/* username = */ null, password);
        runInitializer(username, /* password = */ null);
        assertEquals(0, repo.count());
    }

    @Test
    public void superadminCanBeReregisteredToChangePassword() {
        runInitializer(username, password);
        final String newPassword = "newPassword";
        runInitializer(username, newPassword);
        assertSuperadminPasswordIs(newPassword);
    }

    private void runInitializer(@Nullable final String username, @Nullable final String password) {
        new SuperadminInitializer(userService, username, password).run();
    }

    private void assertSuperadminPasswordIs(@NonNull final String password) {
        final UserDetails superadmin = userService.loadUserByUsername(username);
        assertEquals(password, superadmin.getPassword());
    }
}
