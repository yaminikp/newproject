package com.us.itp.odl.controller;

import static java.util.Collections.emptyMap;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.us.itp.odl.dto.JsonDto;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
abstract class CrudControllerBaseTests {

    @Autowired MockMvc mvc;

    @Autowired private ObjectMapper jsonMapper;

    @SuppressWarnings("SameParameterValue")
    @NonNull final MockHttpServletRequestBuilder createResource(
            @NonNull final String url,
            @NonNull final JsonDto dto
    ) throws JsonProcessingException {
        return createResource(url, dto.asJson());
    }

    @NonNull private MockHttpServletRequestBuilder createResource(
            @NonNull final String url,
            @NonNull final String json
    ) {
        return post(url).contentType(MediaType.APPLICATION_JSON).content(json).with(csrf());
    }

    @SuppressWarnings("SameParameterValue")
    @NonNull final <D> JsonDto<D, ?> emptyJdto(Class<D> dtoClass) {
        return new JsonDto<>(dtoClass, jsonMapper, emptyMap());
    }

    @SuppressWarnings("SameParameterValue")
    @NonNull final <D, T> JsonDto<D, T> jsonDto(Class<D> dtoClass, T jsonPayload) {
        return new JsonDto<>(dtoClass, jsonMapper, jsonPayload);
    }
}
