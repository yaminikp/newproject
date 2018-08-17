package com.us.itp.odl.validation;

import com.us.itp.odl.dto.OfficeAdminDto;

public interface OfficeAdminJdtoValidationTester extends JsonDtoValidationTester<OfficeAdminDto> {

    /* private */ String OFFICE_CODE = "officeCode";
    /* private */ String FIRST_NAME = "firstName";
    /* private */ String MIDDLE_NAME = "middleName";
    /* private */ String LAST_NAME = "lastName";
    /* private */ String GENDER = "gender";
    /* private */ String DOB = "dateOfBirth";
    /* private */ String ADDRESS = "address";
    /* private */ String PHONE_NUMBER = "phoneNumber";
    /* private */ String EMAIL = "email";
    /* private */ String PASSWORD = "password";

    /* private */ String DATE_FORMAT = "dd/MM/yyyy";

    default void assertResourceValidatedAsOfficeAdmin() throws Exception {
        assertAttributesValidatedAsNonBlank(OFFICE_CODE, FIRST_NAME, LAST_NAME, GENDER, DOB,
                ADDRESS, EMAIL, PHONE_NUMBER, PASSWORD);
        assertAttributeMayBeBlank(MIDDLE_NAME);
        assertAttributesValidatedAsThreeLetterCodes(OFFICE_CODE);
        assertAttributesValidatedAsNames(FIRST_NAME, MIDDLE_NAME, LAST_NAME);
        assertAttributeValidatedAsGender(GENDER);
        assertAttributeValidatedAsDateInPast(DOB, DATE_FORMAT);
        assertAttributeValidatedAsAddress(ADDRESS);
        assertAttributeValidatedAsEmail(EMAIL);
        assertAttributeValidatedAsPhoneNumber(PHONE_NUMBER);
        assertAttributeValidatedAsPassword(PASSWORD);
    }
}
