package com.us.itp.odl.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.us.itp.odl.model.Customer;
import com.us.itp.odl.model.TestUser;
import com.us.itp.odl.model.User;
import java.util.UUID;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserRepositoryTests extends RepositoryBaseTests<User, UserRepository> {

    @Rule public final ExpectedException expected = ExpectedException.none();

    public UserRepositoryTests() {
        super(UserRepository.class);
    }

    @Override
    @NonNull final User makeTestEntity() {
        return makeTestEntity(randomUsername());
    }

    @NonNull private static User makeTestEntity(@NonNull final String username) {
        return new TestUser(username);
    }

    @NonNull private static String randomUsername() {
        return UUID.randomUUID().toString();
    }

    @Test
    public void canFindUserByUsername() {
        final User user = makeTestEntity();
        repo.saveAndFlush(user);
        assertEquals(user, repo.getByUsername(user.getUsername()));
    }

    @Test
    public void cantFindNonexistentUser() {
        final User user = makeTestEntity();
        repo.saveAndFlush(user);
        assertNull(repo.getByUsername("not" + user.getUsername()));
    }

    @Test
    public void usernamesAreUnique() {
        final String name = randomUsername();
        repo.save(makeTestEntity(name));
        expected.expect(DataIntegrityViolationException.class);
        repo.saveAndFlush(makeTestEntity(name));
    }

    @Test
    public void countOfUsersByTypeIsZeroForEmptyRepo() {
        assertEquals(0, repo.countUsersOfType(User.class));
    }

    @Test
    public void countOfUsersByTypeIsAccurate() {
        repo.save(makeTestEntity());
        repo.save(makeTestEntity());
        repo.saveAndFlush(makeTestEntity());
        assertEquals(3, repo.countUsersOfType(TestUser.class));
    }

    @Test
    public void countOfUsersByTypeSeesOnlyRequestedType() {
        repo.saveAndFlush(makeTestEntity());
        assertEquals(0, repo.countUsersOfType(Customer.class));
        assertEquals(1, repo.countUsersOfType(TestUser.class));
    }
}
