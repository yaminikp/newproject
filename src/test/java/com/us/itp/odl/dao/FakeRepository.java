package com.us.itp.odl.dao;

import com.us.itp.odl.model.OdlEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

/**
 * A straightforward implementation of a {@link JpaRepository} backed by a hash map.
 *
 * <p>Most methods are currently stubs that always throw {@link UnsupportedOperationException}.
 * Actual implementations will be substituted as required by tests.
 */
class FakeRepository<T extends OdlEntity> implements JpaRepository<T, Long> {

    @NonNull final Map<Long, T> db = new HashMap<>();

    @Override
    public List<T> findAll() {
        return stub("findAll()");
    }

    @Override
    public List<T> findAll(Sort sort) {
        return stub("findAll(Sort)");
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return stub("findAll(Pageable)");
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return stub("findAll(Example)");
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return stub("findAll(Example, Sort)");
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return stub("findAll(Example, Pageable)");
    }

    @Override
    public List<T> findAllById(Iterable<Long> longs) {
        return stub("findAllById");
    }

    @Override
    @NonNull public T getOne(@NonNull final Long id) {
        return Optional.ofNullable(db.get(id)).orElseThrow(() ->
                new EntityNotFoundException("No user with id " + id)
        );
    }

    @Override
    public Optional<T> findById(Long aLong) {
        return stub("findById");
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return stub("findOne");
    }

    @Override
    public long count() {
        return db.size();
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return stub("count(Example)");
    }

    @Override
    public boolean existsById(Long aLong) {
        return stub("existsById");
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return stub("exists");
    }

    @Override
    public void deleteById(Long aLong) {
        stub("deleteById");
    }

    @Override
    public void delete(T entity) {
        stub("delete");
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        stub("deleteAll(Iterable)");
    }

    @Override
    public void deleteAll() {
        db.clear();
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        stub("deleteInBatch");
    }

    @Override
    public void deleteAllInBatch() {
        stub("deleteAllInBatch");
    }

    @Override
    @NonNull public <S extends T> S save(@NonNull final S entity) {
        if (entity.getId() == 0) initializeId(entity);
        db.put(entity.getId(), entity);
        return entity;
    }

    private <S extends T> void initializeId(@NonNull final S entity) {
        entity.setId(maxId() + 1);
    }

    private long maxId() {
        return db.keySet().stream().max(Long::compare).orElse(0L);
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return stub("saveAll");
    }

    @Override
    public void flush() {
        stub("flush");
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return stub("saveAndFlush");
    }

    // Always throws; returns a generic type as proxy for a proper bottom type.
    private <Nothing> Nothing stub(@NonNull final String methodName) {
        throw new UnsupportedOperationException(
                "FakeUserRepository does not implement " + methodName + " method"
        );
    }
}
