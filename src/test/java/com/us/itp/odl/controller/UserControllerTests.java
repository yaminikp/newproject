package com.us.itp.odl.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.us.itp.odl.dto.CustomerDto;
import com.us.itp.odl.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public final class UserControllerTests {

    @Autowired @NonNull
    private MockMvc mockMvc;

    @Mock private UserService userService;

    @InjectMocks
    private UserController  userController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();
    }

    @Test
    public void canCreateUser() throws Exception {
        CustomerDto user = new CustomerDto(
                /* username = */ "alice",
                /* password = */ "myPassword",
                /* realName = */ "Alice", "Mary", "Smith",
                /* address = */ "123 Main Street",
                /* email = */ "alice@example.com",
                /* phoneNumber = */ "555-555-5555",
                /* aadhaarCardNumber = */ "1234 5678 9012"
        );
        mockMvc.perform(
                post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isCreated());
        verify(userService, times(1)).createUser(user);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
