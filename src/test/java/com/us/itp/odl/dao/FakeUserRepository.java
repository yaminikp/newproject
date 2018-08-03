package com.us.itp.odl.dao;

import static com.us.itp.odl.util.CollectionUtil.getFirst;

import com.us.itp.odl.model.User;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@NoRepositoryBean
public final class FakeUserRepository extends FakeRepository<User> implements UserRepository {

    @Override
    @Nullable public User getByUsername(@NonNull final String username) {
        return getFirst(db.values(), user -> user.getUsername().equals(username));
    }
}
