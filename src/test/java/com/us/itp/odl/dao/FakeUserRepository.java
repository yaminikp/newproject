package com.us.itp.odl.dao;

import static com.us.itp.odl.util.CollectionUtil.getFirst;

import com.us.itp.odl.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * A straightforward implementation of {@link UserRepository} backed by a hash map.
 *
 * <p>Most methods are currently stubs that always throw {@link UnsupportedOperationException}.
 * Actual implementations will be substituted as required by tests.
 */
@NoRepositoryBean
@SuppressWarnings("SpringDataMethodInconsistencyInspection")
public final class FakeUserRepository implements UserRepository {

    @NonNull private final Map<Long, User> db = new HashMap<>();

    @Override
    public List<User> findAll() {
        return stub("findAll()");
    }

    @Override
    public List<User> findAll(Sort sort) {
        return stub("findAll(Sort)");
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return stub("findAll(Pageable)");
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return stub("findAll(Example)");
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return stub("findAll(Example, Sort)");
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return stub("findAll(Example, Pageable");
    }

    @Override
    public List<User> findAllById(Iterable<Long> ids) {
        return stub("findAllById");
    }

    @Override
    @NonNull public User getOne(@NonNull final Long id) {
        return Optional.ofNullable(db.get(id)).orElseThrow(() ->
                new EntityNotFoundException("No user with id " + id)
        );
    }

    @Override
    public Optional<User> findById(Long id) {
        return stub("findById");
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return stub("findOne");
    }

    @Override
    public long count() {
        return stub("count");
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return stub("count(Example)");
    }

    @Override
    public boolean existsById(Long id) {
        return stub("existsById");
    }

    @Override
    public void deleteById(Long id) {
        stub("deleteById");
    }

    @Override
    public void delete(User entity) {
        stub("delete");
    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {
        stub("deleteAll(Iterable<User>)");
    }

    @Override
    public void deleteAll() {
        stub("deleteAll()");
    }

    @Override
    public void deleteInBatch(Iterable<User> entities) {
        stub("deleteInBatch");
    }

    @Override
    public void deleteAllInBatch() {
        stub("deleteAllInBatch");
    }

    @Override
    @NonNull public <S extends User> S save(@NonNull final S user) {
        db.put(user.getId(), user);
        return user;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return stub("saveAll");
    }

    @Override
    public void flush() {
        stub("flush");
    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return stub("saveAndFlush");
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return stub("exists");
    }

    // Always throws; returns a generic type as proxy for a proper bottom type.
    private <T> T stub(@NonNull final String methodName) {
        throw new UnsupportedOperationException(
                "FakeUserRepository does not implement " + methodName + " method"
        );
    }

    @Override
    @Nullable public User getByUsername(@NonNull final String username) {
        return getFirst(db.values(), user -> user.getUsername().equals(username));
    }
}
