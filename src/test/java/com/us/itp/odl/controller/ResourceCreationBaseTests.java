package com.us.itp.odl.controller;

import static java.util.Collections.emptyMap;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.us.itp.odl.dto.JsonDto;
import com.us.itp.odl.validation.JsonDtoValidationTester;
import java.io.Serializable;
import java.util.Collection;
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
public abstract class ResourceCreationBaseTests<T> implements JsonDtoValidationTester<T> {

    @Autowired MockMvc mvc;

    @Autowired private ObjectMapper jsonMapper;

    @NonNull private final Class<T> dtoClass;
    @NonNull private final String createResourceUrl;
    @NonNull private final RequestPostProcessor userProc;

    ResourceCreationBaseTests(
            @NonNull final Class<T> dtoClass,
            @NonNull final String createResourceUrl
    ) {
        this.dtoClass = dtoClass;
        this.createResourceUrl = createResourceUrl;
        this.userProc = request -> request;
    }

    ResourceCreationBaseTests(
            @NonNull final Class<T> dtoClass,
            @NonNull final String createResourceUrl,
            @NonNull final String username,
            @NonNull final Collection<? extends GrantedAuthority> authorities
    ) {
        this.dtoClass = dtoClass;
        this.createResourceUrl = createResourceUrl;
        this.userProc = user(username).authorities(authorities);
    }

    @NonNull private JsonDto<T> emptyJdto() {
        return jdto(emptyMap());
    }

    @NonNull final JsonDto<T> jdto(
            @NonNull final Map<String, Serializable> attributeMap
    ) {
        return new JsonDto<>(dtoClass, jsonMapper, attributeMap);
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
