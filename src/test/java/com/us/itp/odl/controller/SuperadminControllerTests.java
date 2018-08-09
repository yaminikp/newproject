package com.us.itp.odl.controller;

import static com.us.itp.odl.util.MapUtil.entry;
import static com.us.itp.odl.util.MapUtil.mapOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.us.itp.odl.dto.JsonDto;
import com.us.itp.odl.dto.OfficeDto;
import com.us.itp.odl.model.Superadmin;
import com.us.itp.odl.service.OfficeService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.lang.NonNull;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public final class SuperadminControllerTests extends CrudControllerBaseTests {

    public SuperadminControllerTests() {
        super("superadmin", Superadmin.AUTHORITIES);
    }

    @MockBean private OfficeService officeService;

    @Test
    public void canCreateOffice() throws Exception {
        final JsonDto<OfficeDto, ?> jdto = officeJdto();
        mvc.perform(createOffice(jdto)).andExpect(status().isCreated());
        verify(officeService, times(1)).saveOffice(jdto.asDto().toOffice());
    }

    @Test
    public void creatingOfficeRequiresSuperadmin() throws Exception {
        mvc.perform(createOffice().with(anonymous())).andExpect(status().isUnauthorized());
        mvc.perform(createOffice().with(user("user"))).andExpect(status().isForbidden());
    }

    @NonNull private MockHttpServletRequestBuilder createOffice() throws JsonProcessingException {
        return createOffice(officeJdto());
    }

    @NonNull private MockHttpServletRequestBuilder createOffice(
            @NonNull final JsonDto<OfficeDto, ?> jdto
    ) throws JsonProcessingException {
        return createResource("/superadmin/office", jdto);
    }

    @NonNull private JsonDto<OfficeDto, ?> officeJdto() {
        return jsonDto(OfficeDto.class, mapOf(
                entry("name", "Test Office"),
                entry("officeCode", "TOF"),
                entry("cityCode", "NWH"),
                entry("address1", "123 Main St."),
                entry("address2", ""),
                entry("city", "Nowhere"),
                entry("postalCode", "55555"),
                entry("state", "MN"),
                entry("country", "USA"),
                entry("phoneNumber", "555-555-5555"),
                entry("email", "test-office@example.com")
        ));
    }
}
