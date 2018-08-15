package com.us.itp.odl.controller;

import static com.us.itp.odl.util.MapUtil.entry;
import static com.us.itp.odl.util.MapUtil.mapOf;
import static com.us.itp.odl.util.StringUtil.repeat;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singleton;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.us.itp.odl.dto.JsonDto;
import com.us.itp.odl.util.Assertion;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public abstract class CreateResourceBaseTests<T> {

    @Autowired MockMvc mvc;

    @Autowired private ObjectMapper jsonMapper;

    @NonNull private final Class<T> dtoClass;
    @NonNull private final String createResourceUrl;
    @NonNull private final RequestPostProcessor userProc;

    CreateResourceBaseTests(
            @NonNull final Class<T> dtoClass,
            @NonNull final String createResourceUrl
    ) {
        this.dtoClass = dtoClass;
        this.createResourceUrl = createResourceUrl;
        this.userProc = request -> request;
    }

    CreateResourceBaseTests(
            @NonNull final Class<T> dtoClass,
            @NonNull final String createResourceUrl,
            @NonNull final String username,
            @NonNull final Collection<? extends GrantedAuthority> authorities
    ) {
        this.dtoClass = dtoClass;
        this.createResourceUrl = createResourceUrl;
        this.userProc = user(username).authorities(authorities);
    }

    abstract JsonDto<T> prototypeJdto();

    @NonNull private JsonDto<T> emptyJdto() {
        return jdto(emptyMap());
    }

    @NonNull final JsonDto<T> jdto(
            @NonNull final Map<String, Serializable> attributeMap
    ) {
        return new JsonDto<>(dtoClass, jsonMapper, attributeMap);
    }

    abstract void assertResourceIsAccepted(@NonNull JsonDto<T> jdto) throws Exception;
    abstract void assertResourceIsRejected(@NonNull JsonDto<T> jdto) throws Exception;

    final void assertAttributeMustBeNonBlank(
            @NonNull final String attribute
    ) throws Exception {
        assertAttributeValuesRejected(attribute, Arrays.asList("", " ", "\t \r\n", null));
    }

    final void assertAttributeIsSingleLine(
            @NonNull final String attribute
    ) throws Exception {
        assertAttributeValuesRejected(attribute, Arrays.asList(
                "Line 1\nLine 2", "Windows style\r\n", "Column 1\tColumn 2"
        ));
    }

    final void assertAttributeAcceptsAlphanumericNames(
            @NonNull final String attribute
    ) throws Exception {
        assertAttributeAcceptsNames(attribute, /* alphanumeric = */ true);
    }

    final void assertAttributeAcceptsNames(
            @NonNull final String attribute
    ) throws Exception {
        assertAttributeAcceptsNames(attribute, /* alphanumeric = */ false);
    }

    private void assertAttributeAcceptsNames(
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

    final void assertAttributeAcceptsStandardText(
            @NonNull final String attribute
    ) throws Exception {
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

    @SuppressWarnings("SameParameterValue")
    final void assertAttributeAcceptsPostalCodes(
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

    @SuppressWarnings("SameParameterValue")
    final void assertAttributeAcceptsPhoneNumbers(
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

    @SuppressWarnings("SameParameterValue")
    final void assertAttributeAcceptsEmailAddresses(
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

    private void assertAttributeAcceptsEmail(
            @NonNull final String attribute,
            @NonNull final String localPart,
            @NonNull final String domain
    ) throws Exception {
        assertAttributeValuesAccepted(attribute, singleton(constructEmail(localPart, domain)));
    }

    private void assertAttributeRejectsEmail(
            @NonNull final String attribute,
            @NonNull final String localPart,
            @NonNull final String domain
    ) throws Exception {
        assertAttributeValuesRejected(attribute, singleton(constructEmail(localPart, domain)));
    }

    @NonNull private String constructEmail(
            @NonNull final String localPart,
            @NonNull final String domain
    ) {
        return localPart + "@" + domain;
    }

    final void assertAttributeValuesAccepted(
            @NonNull final String attribute,
            @NonNull final Collection<? extends Serializable> values
    ) throws Exception {
        assertOnAttributeValues(attribute, values,
                this::assertResourceIsAccepted, "Resource rejected with %s = `%s`");
    }

    final void assertAttributeValuesRejected(
            @NonNull final String attribute,
            @NonNull final Collection<? extends Serializable> values
    ) throws Exception {
        assertOnAttributeValues(attribute, values,
                this::assertResourceIsRejected, "Resource accepted with %s = `%s`");
    }

    private void assertOnAttributeValues(
            @NonNull final String attribute,
            @NonNull final Collection<? extends Serializable> values,
            @NonNull final Assertion<JsonDto<T>> assertion,
            @NonNull final String failureMessage
    ) throws Exception {
        for (Serializable value : values) {
            try {
                assertion.check(prototypeJdto().with(attribute, value));
            } catch (AssertionError e) {
                throw new AssertionError(String.format(failureMessage, attribute, value), e);
            }
        }
    }

    @NonNull final MockHttpServletRequestBuilder createResource() throws JsonProcessingException {
        return createResource(prototypeJdto());
    }

    @NonNull final MockHttpServletRequestBuilder createResource(
            @NonNull final JsonDto<T> jdto
    ) throws JsonProcessingException {
        return createResource(jdto.asJson());
    }

    @NonNull private MockHttpServletRequestBuilder createResource(
            @NonNull final String json
    ) {
        return post(createResourceUrl).contentType(MediaType.APPLICATION_JSON).content(json)
                .with(userProc).with(csrf());
    }

    @Test
    public final void validResourceIsAccepted() throws Exception {
        assertResourceIsAccepted(prototypeJdto());
    }

    @Test
    public final void emptyResourceIsRejected() throws Exception {
        assertResourceIsRejected(emptyJdto());
    }
}
