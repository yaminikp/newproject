package com.us.itp.odl.dao;

import com.us.itp.odl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Returns the number of users in the repository with the specified type.
     *
     * Note that inheritance is not considered, only the most specific type, so
     * {@code countUsersOfType(User.class)} will generally return 0.
     */
    @Query("select count(user) from User user where type(user) = ?1")
    <T extends User> long countUsersOfType(@NonNull final Class<T> userType);

    @Query("select user from User user where user.username = ?1")
    @Nullable User getByUsername(@NonNull final String username);
}
