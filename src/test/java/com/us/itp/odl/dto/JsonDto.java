package com.us.itp.odl.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;

@AllArgsConstructor
public final class JsonDto<D, T> {

    @NonNull private final Class<D> dtoClass;
    @NonNull private final ObjectMapper jsonMapper;
    @NonNull private final T jsonPayload;

    @NonNull public String asJson() throws JsonProcessingException {
        return jsonMapper.writeValueAsString(jsonPayload);
    }

    @NonNull public D asDto() throws IOException {
        return jsonMapper.readValue(this.asJson(), dtoClass);
    }

    @NonNull public JsonDto<D, T> modify(Consumer<T> transform) {
        transform.accept(jsonPayload);
        return new JsonDto<>(dtoClass, jsonMapper, jsonPayload);
    }
}
