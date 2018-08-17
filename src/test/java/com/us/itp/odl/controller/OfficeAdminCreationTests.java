package com.us.itp.odl.controller;

import static com.us.itp.odl.util.MapUtil.entry;
import static com.us.itp.odl.util.MapUtil.mapOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.us.itp.odl.dto.JsonDto;
import com.us.itp.odl.dto.OfficeAdminDto;
import com.us.itp.odl.model.Office;
import com.us.itp.odl.model.Superadmin;
import com.us.itp.odl.model.TestModels;
import com.us.itp.odl.service.OfficeService;
import com.us.itp.odl.service.UserService;
import com.us.itp.odl.validation.OfficeAdminJdtoValidationTester;
import java.io.Serializable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.lang.NonNull;

public class OfficeAdminCreationTests extends ResourceCreationBaseTests<OfficeAdminDto>
        implements OfficeAdminJdtoValidationTester {

    @MockBean private UserService userService;
    @MockBean private OfficeService officeService;

    public OfficeAdminCreationTests() {
        super(OfficeAdminDto.class, "/superadmin/office-admin",
                "superadmin", Superadmin.AUTHORITIES);
    }

    @Before
    public void prepareMocks() {
        when(officeService.lookupOffice(any())).thenAnswer(invocation ->
                TestModels.getOffice(invocation.getArgument(0)));
    }

    @After
    public void resetMocks() {
        Mockito.reset(userService);
    }

    @Override
    @NonNull public JsonDto<OfficeAdminDto> prototypeJdto() {
        return jdto(mapOf(
                entry("officeCode", "TOF"),
                entry("firstName", "Bob"),
                entry("middleName", ""),
                entry("lastName", "Howard"),
                entry("gender", "male"),
                entry("dateOfBirth", "18/04/1978"),
                entry("address", (Serializable) mapOf(
                        entry("address1", "123 Main St."),
                        entry("address2", "Apt. 5B"),
                        entry("city", "Nowhere"),
                        entry("postalCode", "55555"),
                        entry("state", "MN"),
                        entry("country", "USA")
                )),
                entry("phoneNumber", "555-555-0123"),
                entry("email", "bob@example.com"),
                entry("username", "officeAdmin"),
                entry("password", "sw0rdF!sh")
        ));
    }

    @Override
    public void assertResourceIsAccepted(
            @NonNull final JsonDto<OfficeAdminDto> jdto
    ) throws Exception {
        mvc.perform(createResource(jdto)).andExpect(status().isCreated());
        final Office expectedOffice = TestModels.getOffice(jdto.asDto().getOfficeCode());
        verify(userService, times(1)).saveUser(jdto.asDto().toOfficeAdmin(expectedOffice));
        resetMocks();
    }

    @Override
    public void assertResourceIsRejected(
            @NonNull final JsonDto<OfficeAdminDto> jdto
    ) throws Exception {
        mvc.perform(createResource(jdto)).andExpect(status().isBadRequest());
        verify(userService, times(0)).saveUser(any());
        resetMocks();
    }

    @Test
    public void adminForUnknownOfficeIsRejected() throws Exception {
        when(officeService.lookupOffice(any())).thenReturn(null);
        mvc.perform(createResource(prototypeJdto())).andExpect(status().isConflict());
        verify(userService, times(0)).saveUser(any());
    }

    @Test
    public void creatingOfficeRequiresSuperadmin() throws Exception {
        mvc.perform(createResource().with(anonymous())).andExpect(status().isUnauthorized());
        mvc.perform(createResource().with(user("user"))).andExpect(status().isForbidden());
    }

    @Test
    public void duplicateOfficeAdminIsRejected() throws Exception {
        when(userService.userExists(prototypeJdto().asDto().getUsername())).thenReturn(true);
        mvc.perform(createResource()).andExpect(status().isConflict());
        verify(userService, times(0)).saveUser(any());
    }

    @Test
    public void resourceIsValidated() throws Exception {
        assertResourceValidatedAsOfficeAdmin();
    }
}
