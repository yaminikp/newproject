package com.us.itp.odl.validation;

import com.us.itp.odl.dto.AddressDto;
import com.us.itp.odl.dto.JsonDto;
import java.util.Arrays;
import org.springframework.lang.NonNull;

public interface AddressJdtoValidationTester extends JsonDtoValidationTester<AddressDto> {

    /* private */ String ADDRESS1 = "address1";
    /* private */ String ADDRESS2 = "address2";
    /* private */ String CITY = "city";
    /* private */ String STATE = "state";
    /* private */ String POSTAL_CODE = "postalCode";
    /* private */ String COUNTRY = "country";

    /* final */ default void assertResourceValidatedAsAddress() throws Exception {
        assertAttributesValidatedAsNonBlank(ADDRESS1, CITY, STATE, COUNTRY);
        assertAttributeMayBeBlank(ADDRESS2);
        assertAttributesValidatedAsText(ADDRESS1, ADDRESS2);
        assertAttributesValidatedAsSingleLine(ADDRESS1, ADDRESS2);
        assertAttributesValidatedAsAlphanumericNames(CITY);
        assertAttributesValidatedAsNames(STATE, COUNTRY);
        assertAttributeValidatedAsPostalCode(POSTAL_CODE);
    }

    /* private */ default void assertAttributeValidatedAsPostalCode(
            @NonNull final String attribute
    ) throws Exception {
        assertAttributeValuesAccepted(attribute, Arrays.asList(
                "55406", "55406-1234", // US ZIP codes
                "571120" // Indian PIN code
        ));
        assertAttributeValuesRejected(attribute, Arrays.asList(
                "751NHJ", // Contains letters
                "55 406", // Contains a space
                "3.14159", // Contains a period
                "999", // Too short
                "12345-67890", // Too long
                "123-456-78" // Too many digit blocks
        ));
    }

    @NonNull static AddressJdtoValidationTester wrap(
            @NonNull final JsonDtoValidationTester<AddressDto> wrappedTester
    ) {
        return new AddressJdtoValidationTester() {

            @Override
            @NonNull public final JsonDto<AddressDto> prototypeJdto() {
                return wrappedTester.prototypeJdto();
            }

            @Override
            public final void assertResourceIsAccepted(
                    @NonNull final JsonDto<AddressDto> jdto
            ) throws Exception {
                wrappedTester.assertResourceIsAccepted(jdto);
            }

            @Override
            public final void assertResourceIsRejected(
                    @NonNull final JsonDto<AddressDto> jdto
            ) throws Exception {
                wrappedTester.assertResourceIsRejected(jdto);
            }
        };
    }
}
