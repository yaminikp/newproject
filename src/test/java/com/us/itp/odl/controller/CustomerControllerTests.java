package com.us.itp.odl.controller;

import static com.us.itp.odl.util.MapUtil.entry;
import static com.us.itp.odl.util.MapUtil.mapOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.us.itp.odl.dto.CustomerDto;
import com.us.itp.odl.dto.JsonDto;
import com.us.itp.odl.service.UserService;
import java.util.Arrays;
import java.util.Map;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public final class CustomerControllerTests extends CrudControllerBaseTests {

    @MockBean private UserService userService;

    @Test
    public void canCreateCustomer() throws Exception {
        final JsonDto<CustomerDto, ?> jdto = customerJdto();
        mvc.perform(createCustomer(jdto)).andExpect(status().isCreated());
        verify(userService, times(1)).saveUser(jdto.asDto().toCustomer());
    }

    @Test
    public void duplicateCustomerIsRejected() throws Exception {
        when(userService.userExists("alice@example.com")).thenReturn(true);
        mvc.perform(createCustomer(customerJdto())).andExpect(status().isConflict());
        verify(userService, times(1)).userExists("alice@example.com");
        verify(userService, times(0)).saveUser(any());
    }

    @Test
    public void emptyCustomerIsInvalid() throws Exception {
        assertCustomerIsRejected(emptyJdto(CustomerDto.class));
    }

    @Test
    public void firstNameMustBeNonBlank() throws Exception {
        assertCustomerAttributeMustBeNonBlank("firstName");
    }

    @Test
    public void lastNameMustBeNonBlank() throws Exception {
        assertCustomerAttributeMustBeNonBlank("lastName");
    }

    private void assertCustomerAttributeMustBeNonBlank(
            @NonNull final String attribute
    ) throws Exception {
        for (String value : Arrays.asList("", " ", "\t \r\n", null)) {
            assertCustomerIsRejected(customerJdto(attribute, value));
        }
    }

    private void assertCustomerIsRejected(
            @NonNull final JsonDto<CustomerDto, ?> jdto
    ) throws Exception {
        mvc.perform(createCustomer(jdto)).andExpect(status().isBadRequest());
        verify(userService, times(0)).saveUser(any());
    }

    @NonNull private MockHttpServletRequestBuilder createCustomer(
            @NonNull final JsonDto jdto
    ) throws JsonProcessingException {
        return createResource("/customer", jdto);
    }

    @NonNull private JsonDto<CustomerDto, ?> customerJdto(
            @NonNull final String attribute,
            @Nullable final String value
    ) {
        return customerJdto(mapOf(entry(attribute, value)));
    }

    @NonNull private JsonDto<CustomerDto, ?> customerJdto(
            @NonNull final Map<String, String> diffs
    ) {
        return customerJdto().modify(map -> map.putAll(diffs));
    }

    @NonNull private JsonDto<CustomerDto, Map<String, String>> customerJdto() {
        return jsonDto(CustomerDto.class, mapOf(
                entry("password", "myPassword"),
                entry("firstName", "Alice"),
                entry("middleName", "Mary"),
                entry("lastName", "Smith"),
                entry("gender", "female"),
                entry("dateOfBirth", "25/11/1998"),
                entry("address1", "123 Main Street"),
                entry("address2", "Apt. 318"),
                entry("city", "Woodbury"),
                entry("postalCode", "55125"),
                entry("state", "MN"),
                entry("country", "USA"),
                entry("email", "alice@example.com"),
                entry("phoneNumber", "555-555-5555")
        ));
    }
}
