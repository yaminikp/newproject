package com.us.itp.odl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.us.itp.odl.dao.FakeUserRepository;
import com.us.itp.odl.model.Customer;
import java.time.LocalDate;
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
        final Customer customer = new Customer(
                /* username = */ "alice",
                /* password = */ "myPassword",
                /* realName = */ "Alice", "Mary", "Smith",
                /* gender = */ "female",
                /* dateOfBirth = */ LocalDate.of(2018,10,25),
                /* address1 = */ "123 Main Street",
                /* address2 = */ "apt 318",
                /* city = */ "Woodbury",
                /* pinCode */ "55125",
                /* state = */ "MN",
                /* country = */ "USA",
                /* email = */ "alice@example.com",
                /* phoneNumber = */ "555-555-5555",
                /* aadhaarCardNumber = */ "1234 5678 9012"
        );
        service.saveUser(customer);
        assertEquals(customer, service.lookupUser(customer.getUsername()));
        assertEquals(customer, service.loadUserByUsername(customer.getUsername()));
    }

    @Test
    public void unknownUserIsNotFound() {
        final String unknownUsername = "nemo";
        assertNull(service.lookupUser(unknownUsername));
        expected.expect(UsernameNotFoundException.class);
        service.loadUserByUsername(unknownUsername);
    }
}
