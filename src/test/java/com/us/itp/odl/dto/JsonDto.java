package com.us.itp.odl.dto;

import static com.us.itp.odl.util.MapUtil.append;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.function.UnaryOperator;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@AllArgsConstructor
public final class JsonDto<T> {

    @NonNull private final Class<T> dtoClass;
    @NonNull private final ObjectMapper jsonMapper;
    @NonNull private final Map<String, Serializable> attributeMap;

    @NonNull public String asJson() throws JsonProcessingException {
        return jsonMapper.writeValueAsString(attributeMap);
    }

    @NonNull public T asDto() throws IOException {
        return jsonMapper.readValue(this.asJson(), dtoClass);
    }

    @NonNull public JsonDto<T> with(
            @NonNull final String attribute,
            @Nullable final Serializable value
    ) {
        return this.map(attrs -> append(attrs, attribute, value));
    }

    @NonNull private JsonDto<T> map(
            @NonNull final UnaryOperator<Map<String, Serializable>> transform
    ) {
        return new JsonDto<>(dtoClass, jsonMapper, transform.apply(attributeMap));
    }
}
