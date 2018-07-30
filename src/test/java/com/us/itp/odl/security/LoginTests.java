package com.us.itp.odl.security;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

import com.us.itp.odl.model.TestUser;
import com.us.itp.odl.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public final class LoginTests {

    private static final TestUser user = new TestUser("bullwinkle", "daMoose");

    @MockBean private UserService userService;

    @NonNull @Autowired MockMvc mvc;

    @Before
    public void setup() {
        prepMock(userService);
    }

    private void prepMock(@NonNull final UserService mockService) {
        doThrow(UsernameNotFoundException.class).when(mockService).loadUserByUsername(anyString());
        doReturn(user).when(mockService).loadUserByUsername(user.getUsername());
    }

    @Test
    public void validUserCanLogin() throws Exception {
        mvc.perform(loginAs(user)).andExpect(authenticated());
    }

    @Test
    public void unknownUserCantLogin() throws Exception {
        final TestUser badnik = new TestUser("boris", "diemoose");
        mvc.perform(loginAs(badnik)).andExpect(unauthenticated());
    }

    @NonNull private FormLoginRequestBuilder loginAs(@NonNull final TestUser user) {
        return formLogin().user(user.getUsername()).password(user.getRawPassword());
    }
}
