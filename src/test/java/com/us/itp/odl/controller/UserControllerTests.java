package com.us.itp.odl.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.us.itp.odl.dto.CustomerDto;
import com.us.itp.odl.service.UserService;
import java.util.HashMap;
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

    @Autowired @NonNull private MockMvc mvc;
    @Autowired @NonNull private ObjectMapper jsonMapper;
    @MockBean private UserService userService;

    @Test
    public void canCreateUser() throws Exception {
        final String json = jsonMapper.writeValueAsString(makeTestData());
        final CustomerDto dto = jsonMapper.readValue(json, CustomerDto.class);
        mvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON).content(json)
                .with(csrf())
        ).andExpect(status().isCreated());
        verify(userService, times(1)).saveUser(dto.toCustomer());
    }

    @NonNull private HashMap<String, String>  makeTestData() {
        final HashMap<String, String> map = new HashMap<>();
        map.put("username", "alice");
        map.put("password", "myPassword");
        map.put("firstName", "Alice");
        map.put("middleName", "Mary");
        map.put("lastName", "Smith");
        map.put("gender", "female");
        map.put("dateOfBirth", "25/11/1998");
        map.put("address1", "123 Main Street");
        map.put("address2", "apt 318");
        map.put("city", "Woodbury");
        map.put("postalCode", "55125");
        map.put("state", "MN");
        map.put("country", "USA");
        map.put("email", "alice@example.com");
        map.put("phoneNumber", "555-555-5555");
        map.put("aadhaarCardNumber", "1234 5678 9012");
        return map;
    }
}
