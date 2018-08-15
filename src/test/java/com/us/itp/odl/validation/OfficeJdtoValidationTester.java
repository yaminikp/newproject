package com.us.itp.odl.validation;

import com.us.itp.odl.dto.OfficeDto;
import java.util.Arrays;
import org.springframework.lang.NonNull;

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

    /* private */ default void assertAttributesValidatedAsThreeLetterCodes(
            @NonNull final String... attributes
    ) throws Exception {
        for (final String attribute : attributes) {
            assertAttributeValuesAccepted(attribute, Arrays.asList("ABC", "XYZ", "AAA"));
            assertAttributeValuesRejected(attribute, Arrays.asList(
                    "A", "ABCD", "abc", "AÉI", "P2P", "A@B", "A-Z", "", null
            ));
        }
    }
}
