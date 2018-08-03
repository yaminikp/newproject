package com.us.itp.odl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.us.itp.odl.dao.FakeOfficeRepository;
import com.us.itp.odl.model.Office;
import com.us.itp.odl.model.TestModels;
import org.junit.Test;

public final class OfficeServiceTests {

    private final OfficeService service = new OfficeServiceImpl(new FakeOfficeRepository());

    @Test
    public void createdOfficeIsPersisted() {
        final Office office = TestModels.getOffice();
        service.saveOffice(office);
        assertEquals(office, service.lookupOffice(office.getOfficeCode()));
    }

    @Test
    public void unknownOfficeIsNotFound() {
        final String unknownOfficeCode = "ABC";
        assertNull(service.lookupOffice(unknownOfficeCode));
    }
}
