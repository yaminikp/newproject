package com.us.itp.odl.validation;

import static com.us.itp.odl.util.CollectionUtil.mapToList;
import static com.us.itp.odl.util.DateUtil.dateFormatter;
import static com.us.itp.odl.util.MapUtil.entry;
import static com.us.itp.odl.util.MapUtil.mapOf;
import static com.us.itp.odl.util.StringUtil.repeat;
import static java.util.Collections.singleton;

import com.us.itp.odl.dto.AddressDto;
import com.us.itp.odl.dto.JsonDto;
import com.us.itp.odl.util.Assertion;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.lang.NonNull;

public interface JsonDtoValidationTester<T> {

    @NonNull JsonDto<T> prototypeJdto();
    void assertResourceIsAccepted(@NonNull JsonDto<T> jdto) throws Exception;
    void assertResourceIsRejected(@NonNull JsonDto<T> jdto) throws Exception;

    /* protected final */ default void assertAttributesValidatedAsNonBlank(
            @NonNull final String... attributes
    ) throws Exception {
        for (final String attribute : attributes) {
            assertAttributeValuesRejected(attribute, Arrays.asList("", " ", "\t \r\n", null));
        }
    }

    /* protected final */ default void assertAttributeMayBeBlank(
            @NonNull final String attribute
    ) throws Exception {
        assertResourceIsAccepted(prototypeJdto().with(attribute, ""));
    }

    /* protected final */ default void assertAttributesValidatedAsAlphanumericNames(
            @NonNull final String... attributes
    ) throws Exception {
        for (final String attribute : attributes) {
            assertAttributesValidatedAsNames(attribute, /* alphanumeric = */ true);
        }
    }

    /* protected final */ default void assertAttributesValidatedAsNames(
            @NonNull final String... attributes
    ) throws Exception {
        for (final String attribute : attributes) {
            assertAttributesValidatedAsNames(attribute, /* alphanumeric = */ false);
        }
    }

    /* private */ default void assertAttributesValidatedAsNames(
            @NonNull final String attribute,
            @NonNull final boolean alphanumeric
    ) throws Exception {
        assertAttributeValuesRejected(attribute, Arrays.asList(
                "New\tYork", "\"New\" York", "Minne@polis", "Minneapolis\0", "Ranma ½", "Chicago²"
        ));
        assertAttributeValuesAccepted(attribute, Arrays.asList(
                "St. Paul", "O'Leary", "Zoë Smith", "Alice Smith-Smythe", "John/Jane",
                "Orléans", // é as single character
                "Orléans", // é with combining diacritic
                "กรุงเทพมหานคร" // Bangkok in Thai
        ));
        final List<String> alphanumericNames = Arrays.asList(
                "Brainiac 5", "18-Hazari", "305",
                "٠١٢٣٤٥٦٧٨٩" // Arabic numerals 0-9
        );
        if (alphanumeric) assertAttributeValuesAccepted(attribute, alphanumericNames);
        else assertAttributeValuesRejected(attribute, alphanumericNames);
    }

    /* private */ default void assertAttributesValidatedAsText(
            @NonNull final String... attributes
    ) throws Exception {
        for (final String attribute : attributes) {
            assertAttributeValuesRejected(attribute, Arrays.asList(
                    "Some symbols: © ╬ ☺",
                    "Null character: \0",
                    "Form feed: \f",
                    "A selection of non-standard whitespace: \u00a0\u2002\u2007"
            ));
            assertAttributeValuesAccepted(attribute, Arrays.asList(
                    "#", "-/", "'\"", ",.;:", "()[]", "spaces okay", "2º District"
            ));
        }
    }

    /* protected final */ default void assertAttributeValidatedAsGender(
            @NonNull final String attribute
    ) throws Exception {
        assertAttributeValuesAccepted(attribute, Arrays.asList(
                "male", "female", "Male", "Female", "MALE", "FEMALE"
        ));
        assertAttributeValuesRejected(attribute, Arrays.asList(
                null, "", "0", "1", "M", "F", "other text"
        ));
    }

    /* protected final */ default void assertAttributeValidatedAsPhoneNumber(
            @NonNull final String attribute
    ) throws Exception {
        assertAttributeValuesAccepted(attribute, Arrays.asList(
                "555-0100", "763-555-0199", "+1 763-555-0199", // United States (hyphens)
                "555 0100", "763 555 0199", "+1 763 555 0199", // United States (spaces)
                "80-99999999", "020-3030303", "07582-221434", "+91 4444-123456", // India (hyphens)
                "80 99999999", "020 3030303", "07582 221434", "+91 4444 123456", // India (spaces)
                "8099999999", "0203030303", "07582221434", "+91 4444123456", // India (no separator)
                "+123 123-456-78901234" // Max length
        ));
        assertAttributeValuesRejected(attribute, Arrays.asList(
                "555-0ARF", // Contains letters
                "555/0100", "555.0100", "555\\0100", "555_0100", "555+0100", // Bad separators
                "555☺0100", "555\n0100", "555\t0100", "555~0100", // More bad separators
                "+91", // Just the country code
                "+914444123456", // No space following country code
                "Not a phone number", "555-010\0", "alice@example.com", "*69",
                "555\u00a00100", // Contains non-breaking space
                "-5555555", "5555555-", "555--5555", // Hyphen as non-separator
                " 5555555", "5555555 ", "555  5555", // Space as non-separator
                "1", "+123 4", "123456789012345", "+123 123-456-789012345" // Too short or too long
        ));
    }

    /* protected final */ default void assertAttributeValidatedAsEmail(
            @NonNull final String attribute
    ) throws Exception {
        final Map<String, Boolean> localPartsMap = mapOf(
                entry("alice", true),
                entry("Alice", true),
                entry("alice123", true),
                entry("007alice", true),
                entry("alice!#$%&'*+-/=?^_`{|}~", true),
                entry("alice.smith", true),
                entry("alice smith", false),
                entry("(alice)", false),
                entry("[alice]", false),
                entry("<alice>", false),
                entry("alice,smith", false),
                entry("alice:smith", false),
                entry("alice;smith", false),
                entry("alice@smith", false),
                entry(".alice", false),
                entry("alice.", false),
                entry("alice..smith", false),
                entry("Alice \\\"the Owl\\\" Smith", false),
                entry("Alice\"the Owl\" Smith", false),
                entry("Alice\\Smith", false),
                entry("alice\0", false),
                entry("alice\tsmith", false),
                entry("alice\nsmith", false),
                entry("LocalPartsOfEmailAddressesCanHaveUpTo64CharactersWhichIsJustThis", true),
                entry("LocalPartsOfEmailAddressesCanHaveNoMoreThan64CharactersAndThisHas67", false)
        );
        final Map<String, Boolean> domainsMap = mapOf(
                entry("example.com", true),
                entry("email.example.com", true),
                entry("example.example", true),
                entry("EXAMPLE.COM", true),
                entry("my-email.example.com", true),
                entry("my email.example.com", false),
                entry("-email.example.com", false),
                entry(repeat(".com", 64).replaceFirst("[.]", ""), true), // Length 255 okay
                entry(repeat(".com", 64).replaceFirst("[.]", "x"), false) // Length 256 too long
        );
        for (final String localPart : localPartsMap.keySet()) {
            for (final String domain : domainsMap.keySet()) {
                if (localPartsMap.get(localPart) && domainsMap.get(domain))
                    assertAttributeAcceptsEmail(attribute, localPart, domain);
                else assertAttributeRejectsEmail(attribute, localPart, domain);
            }
        }
    }

    /* private */ default void assertAttributeAcceptsEmail(
            @NonNull final String attribute,
            @NonNull final String localPart,
            @NonNull final String domain
    ) throws Exception {
        assertAttributeValuesAccepted(attribute, singleton(constructEmail(localPart, domain)));
    }

    /* private */ default void assertAttributeRejectsEmail(
            @NonNull final String attribute,
            @NonNull final String localPart,
            @NonNull final String domain
    ) throws Exception {
        assertAttributeValuesRejected(attribute, singleton(constructEmail(localPart, domain)));
    }

    @NonNull /* private */ default String constructEmail(
            @NonNull final String localPart,
            @NonNull final String domain
    ) {
        return localPart + "@" + domain;
    }

    /* protected final */ default void assertAttributeValidatedAsDateInPast(
            @NonNull final String attribute,
            @NonNull final String dateFormat
    ) throws Exception {
        assertAttributeAcceptsDates(attribute, Arrays.asList(
                LocalDate.of(2018, Month.AUGUST, 9),
                LocalDate.of(1900, Month.JANUARY, 1),
                LocalDate.now().minusDays(1)
        ), dateFormat);
        assertAttributeRejectsDates(attribute, Arrays.asList(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusYears(1)
        ), dateFormat);
    }

    /* private */ default void assertAttributeAcceptsDates(
            @NonNull final String attribute,
            @NonNull final Collection<LocalDate> dates,
            @NonNull final String dateFormat
    ) throws Exception {
        assertAttributeValuesAccepted(attribute, mapToList(dates, dateFormatter(dateFormat)));
    }

    /* private */ default void assertAttributeRejectsDates(
            @NonNull final String attribute,
            @NonNull final Collection<LocalDate> dates,
            @NonNull final String dateFormat
    ) throws Exception {
        assertAttributeValuesRejected(attribute, mapToList(dates, dateFormatter(dateFormat)));
    }

    /* protected final */ default void assertAttributeValidatedAsAddress(
            @NonNull final String attribute
    ) throws Exception {
        final AddressJdtoValidationTester addressTester = AddressJdtoValidationTester.wrap(
                new SubJdtoValidationTester<>(this, AddressDto.class, attribute)
        );
        addressTester.assertResourceValidatedAsAddress();
    }

    /* private */ default void assertAttributesValidatedAsSingleLine(
            @NonNull final String... attributes
    ) throws Exception {
        for (final String attribute : attributes) {
            assertAttributeValuesRejected(attribute, Arrays.asList(
                    "Line 1\nLine 2", "Windows style\r\n", "Column 1\tColumn 2"
            ));
        }
    }

    /* protected final */ default void assertAttributeValidatedAsPassword(
            @NonNull final String attribute
    ) throws Exception {
        assertAttributeValuesAccepted(attribute, Arrays.asList(
                "P@ssw0rd", "Pa$$w0rd",
                "AReallyLongPasswordIsOkayAsLongAsItHasAllCharTypes!!!1!",
                "Π@ςςυ0ρδ" // Uses non-Latin characters to satisfy letter requirements
        ));
        assertAttributeValuesRejected(attribute, Arrays.asList(
                "P@SSW0RD", // No lower case letters
                "p@ssw0rd", // No upper case letters
                "P@ssword", // No numbers
                "Password", // No punctuation
                "P@ssw0r", // Too short
                "P@ss w0rd", // Contains space
                "P@ssw0rd\r\n\t", // Contains other whitespace
                "P@ssw0\0rd", // Contains null character
                "P@ss\u00a0w0rd" // Contains non-breaking space
        ));
    }

    /* protected final */ default void assertAttributesValidatedAsThreeLetterCodes(
            @NonNull final String... attributes
    ) throws Exception {
        for (final String attribute : attributes) {
            assertAttributeValuesAccepted(attribute, Arrays.asList("ABC", "XYZ", "AAA"));
            assertAttributeValuesRejected(attribute, Arrays.asList(
                    "A", "ABCD", "abc", "AÉI", "P2P", "A@B", "A-Z", "", null
            ));
        }
    }

    /* protected final */ default void assertAttributeValuesAccepted(
            @NonNull final String attribute,
            @NonNull final Collection<? extends Serializable> values
    ) throws Exception {
        assertOnAttributeValues(attribute, values,
                this::assertResourceIsAccepted, "Resource rejected with %s = `%s`");
    }

    /* protected final */ default void assertAttributeValuesRejected(
            @NonNull final String attribute,
            @NonNull final Collection<? extends Serializable> values
    ) throws Exception {
        assertOnAttributeValues(attribute, values,
                this::assertResourceIsRejected, "Resource accepted with %s = `%s`");
    }

    /* private */ default void assertOnAttributeValues(
            @NonNull final String attribute,
            @NonNull final Collection<? extends Serializable> values,
            @NonNull final Assertion<JsonDto<T>> assertion,
            @NonNull final String failureMessage
    ) throws Exception {
        for (final Serializable value : values) {
            try {
                assertion.check(prototypeJdto().with(attribute, value));
            } catch (final AssertionError e) {
                throw new AssertionError(String.format(failureMessage, attribute, value), e);
            }
        }
    }
}
