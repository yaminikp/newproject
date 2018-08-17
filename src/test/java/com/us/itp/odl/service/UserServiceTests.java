package com.us.itp.odl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.us.itp.odl.dao.FakeUserRepository;
import com.us.itp.odl.model.TestModels;
import com.us.itp.odl.model.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public final class UserServiceTests {

    @Rule public final ExpectedException expected = ExpectedException.none();

    @NonNull private final UserService service = new UserServiceImpl(new FakeUserRepository());

    @Test
    public void createdCustomerIsPersisted() {
        createdUserIsPersisted(TestModels.getCustomer());
    }

    @Test
    public void createdOfficeAdminIsPersisted() {
        createdUserIsPersisted(TestModels.getOfficeAdmin());
    }

    private void createdUserIsPersisted(@NonNull final User user) {
        service.saveUser(user);
        assertEquals(user, service.lookupUser(user.getUsername()));
        assertEquals(user, service.loadUserByUsername(user.getUsername()));
    }

    @Test
    public void unknownUserIsNotFound() {
        final String unknownUsername = "nemo";
        assertNull(service.lookupUser(unknownUsername));
        expected.expect(UsernameNotFoundException.class);
        service.loadUserByUsername(unknownUsername);
    }
}
