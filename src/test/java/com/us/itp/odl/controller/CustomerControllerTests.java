package com.us.itp.odl.controller;

import static com.us.itp.odl.util.MapUtil.entry;
import static com.us.itp.odl.util.MapUtil.mapOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.us.itp.odl.dto.CustomerDto;
import com.us.itp.odl.dto.JsonDto;
import com.us.itp.odl.service.UserService;
import com.us.itp.odl.validation.CustomerJdtoValidationTester;
import java.io.Serializable;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.lang.NonNull;

public final class CustomerControllerTests extends CreateResourceBaseTests<CustomerDto>
        implements CustomerJdtoValidationTester {

    @MockBean
    private UserService userService;

    public CustomerControllerTests() {
        super(CustomerDto.class, "/customer");
    }

    @Override
    @NonNull
    public JsonDto<CustomerDto> prototypeJdto() {
        return jdto(mapOf(
                entry("firstName", "Alice"),
                entry("middleName", "Mary"),
                entry("lastName", "Smith"),
                entry("gender", "female"),
                entry("dateOfBirth", "25/11/1998"),
                entry("address", (Serializable) mapOf(
                        entry("address1", "123 Main Street"),
                        entry("address2", "Apt. 318"),
                        entry("city", "Woodbury"),
                        entry("postalCode", "55125"),
                        entry("state", "MN"),
                        entry("country", "USA")
                )),
                entry("phoneNumber", "555-555-5555"),
                entry("email", "alice@example.com"),
                entry("password", "P@ssw0rd")
        ));
    }

    @Override
    public void assertResourceIsAccepted(
            @NonNull final JsonDto<CustomerDto> jdto
    ) throws Exception {
        mvc.perform(createResource(jdto)).andExpect(status().isCreated());
        verify(userService, times(1)).saveUser(jdto.asDto().toCustomer());
        Mockito.reset(userService);
    }

    @Override
    public void assertResourceIsRejected(
            @NonNull final JsonDto<CustomerDto> jdto
    ) throws Exception {
        mvc.perform(createResource(jdto)).andExpect(status().isBadRequest());
        verify(userService, times(0)).saveUser(any());
        Mockito.reset(userService);
    }

    @Test
    public void resourceIsValidated() throws Exception {
        assertResourceValidatedAsCustomer();
    }

    @Test
    public void duplicateCustomerIsRejected() throws Exception {
        when(userService.userExists("alice@example.com")).thenReturn(true);
        mvc.perform(createResource()).andExpect(status().isConflict());
        verify(userService, times(1)).userExists("alice@example.com");
        verify(userService, times(0)).saveUser(any());
    }
}
