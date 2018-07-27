package com.us.itp.odl.dao;

import com.us.itp.odl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user from User user where user.username = ?1")
    @Nullable User getByUsername(@NonNull final String username);
}
