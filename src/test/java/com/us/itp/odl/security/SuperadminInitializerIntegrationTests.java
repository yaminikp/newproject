package com.us.itp.odl.security;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import com.us.itp.odl.dao.UserRepository;
import com.us.itp.odl.model.Superadmin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "odl.superadmin.username=bossMan",
        "odl.superadmin.password=swordfish"
})
public final class SuperadminInitializerIntegrationTests {

    @MockBean UserRepository repo;

    @Value("${odl.superadmin.username}") String username;
    @Value("${odl.superadmin.password}") String password;

    @Test
    public void superadminIsAutomaticallyRegisteredOnStartup() {
        verify(repo).save(argThat(user ->
                user instanceof Superadmin
                && user.getUsername().equals(username)
                && user.getPassword().equals(password)
        ));
    }
}
