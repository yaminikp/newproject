package com.us.itp.odl.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.us.itp.odl.dto.CustomerDto;
import com.us.itp.odl.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public final class UserControllerTests {

    @Autowired @NonNull private MockMvc mockMvc;
    @Autowired @NonNull private ObjectMapper jsonMapper;

    @MockBean private UserService userService;

    @Test
    public void canCreateUser() throws Exception {
        CustomerDto dto = new CustomerDto(
                /* username = */ "alice",
                /* password = */ "myPassword",
                /* realName = */ "Alice", "Mary", "Smith",
                /* address = */ "123 Main Street",
                /* email = */ "alice@example.com",
                /* phoneNumber = */ "555-555-5555",
                /* aadhaarCardNumber = */ "1234 5678 9012"
        );
        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
        verify(userService, times(1)).createUser(dto.toCustomer());
    }
}
