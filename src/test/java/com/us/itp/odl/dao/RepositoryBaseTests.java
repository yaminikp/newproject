package com.us.itp.odl.dao;

import static org.junit.Assert.assertEquals;

import com.us.itp.odl.model.OdlEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class RepositoryBaseTests<T extends OdlEntity, R extends JpaRepository<T, Long>> {

    @Autowired private ApplicationContext context;
    private final Class<R> repoClass;
    R repo;

    @NonNull abstract T makeTestEntity();

    RepositoryBaseTests(@NonNull final Class<R> repoClass) {
        this.repoClass = repoClass;
    }

    @Before
    public void initRepo() {
        if (repo == null) repo = context.getBean(repoClass);
    }

    @Test
    public void repoIsInitiallyEmpty() {
        assertEquals(0, repo.count());
    }

    @Test
    public void savedEntityAddsToCount() {
        repo.saveAndFlush(makeTestEntity());
        assertEquals(1, repo.count());
    }

    @Test
    public void canFindSavedEntity() {
        final T entity = makeTestEntity();
        assertEquals(entity, repo.saveAndFlush(entity));
        assertEquals(entity, repo.getOne(entity.getId()));
    }
}
