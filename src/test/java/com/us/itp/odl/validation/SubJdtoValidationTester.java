package com.us.itp.odl.validation;

import com.us.itp.odl.dto.JsonDto;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;

@AllArgsConstructor
public class SubJdtoValidationTester<T, P> implements JsonDtoValidationTester<T> {

    @NonNull private final JsonDtoValidationTester<P> parentTester;
    @NonNull private final Class<T> dtoClass;
    @NonNull private final String attributeInParent;

    @Override
    @NonNull public JsonDto<T> prototypeJdto() {
        return parentTester.prototypeJdto().subJdtoAt(dtoClass, attributeInParent);
    }

    @Override
    public void assertResourceIsAccepted(@NonNull final JsonDto<T> jdto) throws Exception {
        parentTester.assertResourceIsAccepted(embedInParent(jdto));
    }

    @Override
    public void assertResourceIsRejected(@NonNull final JsonDto<T> jdto) throws Exception {
        parentTester.assertResourceIsRejected(embedInParent(jdto));
    }

    @NonNull private JsonDto<P> embedInParent(@NonNull final JsonDto<T> jdto) {
        return parentTester.prototypeJdto().with(attributeInParent, jdto);
    }
}
