package com.us.itp.odl.validation;

import com.us.itp.odl.dto.CustomerDto;

public interface CustomerJdtoValidationTester extends JsonDtoValidationTester<CustomerDto> {

    /* private */ String FIRST_NAME = "firstName";
    /* private */ String MIDDLE_NAME = "middleName";
    /* private */ String LAST_NAME = "lastName";
    /* private */ String GENDER = "gender";
    /* private */ String DOB = "dateOfBirth";
    /* private */ String ADDRESS = "address";
    /* private */ String EMAIL = "email";
    /* private */ String PHONE_NUMBER = "phoneNumber";
    /* private */ String PASSWORD = "password";

    /* private */ String DATE_FORMAT = "dd/MM/yyyy";

    /* final */ default void assertResourceValidatedAsCustomer() throws Exception {
        assertAttributesValidatedAsNonBlank(
                FIRST_NAME, LAST_NAME, GENDER, DOB, ADDRESS, EMAIL, PHONE_NUMBER, PASSWORD);
        assertAttributeMayBeBlank(MIDDLE_NAME);
        assertAttributesValidatedAsNames(FIRST_NAME, MIDDLE_NAME, LAST_NAME);
        assertAttributeValidatedAsGender(GENDER);
        assertAttributeValidatedAsDateInPast(DOB, DATE_FORMAT);
        assertAttributeValidatedAsAddress(ADDRESS);
        assertAttributeValidatedAsEmail(EMAIL);
        assertAttributeValidatedAsPhoneNumber(PHONE_NUMBER);
        assertAttributeValidatedAsPassword(PASSWORD);
    }
}
