package com.us.itp.odl.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import com.us.itp.odl.model.Office;
import com.us.itp.odl.model.TestModels;
import org.junit.Test;
import org.springframework.lang.NonNull;

public class OfficeRepositoryTests extends RepositoryBaseTests<Office, OfficeRepository> {

    public OfficeRepositoryTests() {
        super(OfficeRepository.class);
    }

    @Override
    @NonNull Office makeTestEntity() {
        return TestModels.getOffice();
    }

    @Test
    public void canFindOfficeByCode() {
        final Office office = makeTestEntity();
        repo.saveAndFlush(office);
        assertEquals(office, repo.getByCode(office.getOfficeCode()));
    }

    @Test
    public void cantFindNonexistentOffice() {
        final Office office = makeTestEntity();
        final String nonExistentCode = "XXX";
        assertNotEquals(nonExistentCode, office.getOfficeCode());
        repo.saveAndFlush(office);
        assertNull(repo.getByCode(nonExistentCode));
    }
}
