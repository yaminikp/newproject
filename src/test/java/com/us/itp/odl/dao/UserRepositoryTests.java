package com.us.itp.odl.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.us.itp.odl.model.TestUser;
import com.us.itp.odl.model.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class UserRepositoryTests {

    @Rule public final ExpectedException expected = ExpectedException.none();

    @Autowired @NonNull UserRepository repo;

    @Test
    public void repoIsInitiallyEmpty() {
        assertEquals(0, repo.count());
    }

    @Test
    public void savedUserAddsToCount() {
        repo.saveAndFlush(new TestUser("bob"));
        assertEquals(1, repo.count());
    }

    @Test
    public void canFindSavedUser() {
        final User bob = new TestUser("bob");
        assertEquals(bob, repo.saveAndFlush(bob));
        assertEquals(bob, repo.getOne(bob.getId()));
    }

    @Test
    public void canFindUserByUsername() {
        final User bob = new TestUser("bob");
        repo.saveAndFlush(bob);
        assertEquals(bob, repo.getByUsername(bob.getUsername()));
    }

    @Test
    public void cantFindNonexistentUser() {
        final User bob = new TestUser("bob");
        repo.saveAndFlush(bob);
        assertNull(repo.getByUsername("alice"));
    }

    @Test
    public void usernamesAreUnique() {
        final String name = "bob";
        repo.save(new TestUser(name));
        expected.expect(DataIntegrityViolationException.class);
        repo.saveAndFlush(new TestUser(name));
    }
}
