package com.us.itp.odl.controller;

import static com.us.itp.odl.util.MapUtil.entry;
import static com.us.itp.odl.util.MapUtil.mapOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.us.itp.odl.dto.JsonDto;
import com.us.itp.odl.dto.OfficeDto;
import com.us.itp.odl.model.Superadmin;
import com.us.itp.odl.service.OfficeService;
import com.us.itp.odl.validation.OfficeJdtoValidationTester;
import java.io.Serializable;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.lang.NonNull;

public final class OfficeCreationTests extends ResourceCreationBaseTests<OfficeDto>
        implements OfficeJdtoValidationTester {

    @MockBean private OfficeService officeService;

    public OfficeCreationTests() {
        super(OfficeDto.class, "/superadmin/office", "superadmin", Superadmin.AUTHORITIES);
    }

    @Override
    @NonNull public JsonDto<OfficeDto> prototypeJdto() {
        return jdto(mapOf(
                entry("name", "Test Office"),
                entry("officeCode", "TOF"),
                entry("cityCode", "NWH"),
                entry("address", (Serializable) mapOf(
                        entry("address1", "123 Main St."),
                        entry("address2", "Apt. 5B"),
                        entry("city", "Nowhere"),
                        entry("postalCode", "55555"),
                        entry("state", "MN"),
                        entry("country", "USA")
                )),
                entry("phoneNumber", "555-555-5555"),
                entry("email", "test-office@example.com")
        ));
    }

    @Override
    public void assertResourceIsAccepted(@NonNull final JsonDto<OfficeDto> jdto) throws Exception {
        mvc.perform(createResource(jdto)).andExpect(status().isCreated());
        verify(officeService, times(1)).saveOffice(jdto.asDto().toOffice());
        Mockito.reset(officeService);
    }

    @Override
    public void assertResourceIsRejected(@NonNull final JsonDto<OfficeDto> jdto) throws Exception {
        mvc.perform(createResource(jdto)).andExpect(status().isBadRequest());
        verify(officeService, times(0)).saveOffice(any());
        Mockito.reset(officeService);
    }

    @Test
    public void creatingOfficeRequiresSuperadmin() throws Exception {
        mvc.perform(createResource().with(anonymous())).andExpect(status().isUnauthorized());
        mvc.perform(createResource().with(user("user"))).andExpect(status().isForbidden());
    }

    @Test
    public void resourceIsValidated() throws Exception {
        assertResourceValidatedAsOffice();
    }
}
