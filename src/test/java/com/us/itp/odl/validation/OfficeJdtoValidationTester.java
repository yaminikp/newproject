package com.us.itp.odl.validation;

import com.us.itp.odl.dto.OfficeDto;

public interface OfficeJdtoValidationTester extends JsonDtoValidationTester<OfficeDto> {

    /* private */ String OFFICE_NAME = "name";
    /* private */ String OFFICE_CODE = "officeCode";
    /* private */ String CITY_CODE = "cityCode";
    /* private */ String ADDRESS = "address";
    /* private */ String PHONE_NUMBER = "phoneNumber";
    /* private */ String EMAIL = "email";

    /* final */ default void assertResourceValidatedAsOffice() throws Exception {
        assertAttributesValidatedAsNonBlank(
                OFFICE_NAME, OFFICE_CODE, CITY_CODE, PHONE_NUMBER, EMAIL
        );
        assertAttributesValidatedAsAlphanumericNames(OFFICE_NAME);
        assertAttributesValidatedAsThreeLetterCodes(OFFICE_CODE, CITY_CODE);
        assertAttributeValidatedAsAddress(ADDRESS);
        assertAttributeValidatedAsPhoneNumber(PHONE_NUMBER);
        assertAttributeValidatedAsEmail(EMAIL);
    }
}
