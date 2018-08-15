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
import java.util.Arrays;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.lang.NonNull;

public final class SuperadminControllerTests extends CreateResourceBaseTests<OfficeDto> {

    @MockBean private OfficeService officeService;

    public SuperadminControllerTests() {
        super(OfficeDto.class, "/superadmin/office", "superadmin", Superadmin.AUTHORITIES);
    }

    @Override
    @NonNull JsonDto<OfficeDto> prototypeJdto() {
        return jdto(mapOf(
                entry("name", "Test Office"),
                entry("officeCode", "TOF"),
                entry("cityCode", "NWH"),
                entry("address1", "123 Main St."),
                entry("address2", "Apt. 5B"),
                entry("city", "Nowhere"),
                entry("postalCode", "55555"),
                entry("state", "MN"),
                entry("country", "USA"),
                entry("phoneNumber", "555-555-5555"),
                entry("email", "test-office@example.com")
        ));
    }

    @Override
    void assertResourceIsAccepted(@NonNull final JsonDto<OfficeDto> jdto) throws Exception {
        mvc.perform(createResource(jdto)).andExpect(status().isCreated());
        verify(officeService, times(1)).saveOffice(jdto.asDto().toOffice());
        Mockito.reset(officeService);
    }

    @Override
    void assertResourceIsRejected(@NonNull final JsonDto<OfficeDto> jdto) throws Exception {
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
    public void nameMustBeNonBlank() throws Exception {
        assertAttributeMustBeNonBlank("name");
    }

    @Test
    public void nameAcceptsAlphanumericNames() throws Exception {
        assertAttributeAcceptsAlphanumericNames("name");
    }

    @Test
    public void officeAndCityCodesAreThreeLetterCodes() throws Exception {
        assertResourceAttributeIsThreeLetterCode("officeCode");
        assertResourceAttributeIsThreeLetterCode("cityCode");
    }

    private void assertResourceAttributeIsThreeLetterCode(
            @NonNull final String attribute
    ) throws Exception {
        assertAttributeValuesAccepted(attribute, Arrays.asList("ABC", "XYZ", "AAA"));
        assertAttributeValuesRejected(attribute, Arrays.asList(
                "A", "ABCD", "abc", "AÉI", "P2P", "A@B", "A-Z", "", null
        ));
    }

    @Test
    public void onlySecondAddressLineCanBeBlank() throws Exception {
        assertAttributeMustBeNonBlank("address1");
        assertResourceIsAccepted(prototypeJdto().with("address2", ""));
    }

    @Test
    public void addressLinesAcceptStandardText() throws Exception {
        assertAttributeAcceptsStandardText("address1");
        assertAttributeAcceptsStandardText("address2");
    }

    @Test
    public void addressLinesAreSingleLine() throws Exception {
        assertAttributeIsSingleLine("address1");
        assertAttributeIsSingleLine("address2");
    }

    @Test
    public void cityMustBeNonBlank() throws Exception {
        assertAttributeMustBeNonBlank("city");
    }

    @Test
    public void cityAcceptsAlphanumericNames() throws Exception {
        assertAttributeAcceptsAlphanumericNames("city");
    }

    @Test
    public void postalCodeIsAPostalCode() throws Exception {
        assertAttributeAcceptsPostalCodes("postalCode");
    }

    @Test
    public void stateMustBeNonBlank() throws Exception {
        assertAttributeMustBeNonBlank("state");
    }

    @Test
    public void stateAcceptsNames() throws Exception {
        assertAttributeAcceptsNames("state");
    }

    @Test
    public void countryMustBeNonBlank() throws Exception {
        assertAttributeMustBeNonBlank("country");
    }

    @Test
    public void countryAcceptsNames() throws Exception {
        assertAttributeAcceptsNames("country");
    }

    @Test
    public void phoneNumberMustBeNonBlank() throws Exception {
        assertAttributeMustBeNonBlank("phoneNumber");
    }

    @Test
    public void phoneNumberIsAPhoneNumber() throws Exception {
        assertAttributeAcceptsPhoneNumbers("phoneNumber");
    }

    @Test
    public void emailMustBeNonBlank() throws Exception {
        assertAttributeMustBeNonBlank("email");
    }

    @Test
    public void emailIsAnEmail() throws Exception {
        assertAttributeAcceptsEmailAddresses("email");
    }
}
