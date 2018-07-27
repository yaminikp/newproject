package com.us.itp.odl.service;

import static org.junit.Assert.assertEquals;

import com.us.itp.odl.dao.FakeUserRepository;
import com.us.itp.odl.model.Customer;
import org.junit.Test;
import org.springframework.lang.NonNull;

public final class UserServiceTests {

    @NonNull private final UserService service = new UserServiceImpl(new FakeUserRepository());

    @Test
    public void createdCustomerIsPersisted() {
        final Customer customer = new Customer(
                /* username = */ "alice",
                /* password = */ "myPassword",
                /* realName = */ "Alice", "Mary", "Smith",
                /* address = */ "123 Main Street",
                /* email = */ "alice@example.com",
                /* phoneNumber = */ "555-555-5555",
                /* aadhaarCardNumber = */ "1234 5678 9012"
        );
        service.createUser(customer);
        assertEquals(customer, service.lookupUser(customer.getUsername()));
    }
}
