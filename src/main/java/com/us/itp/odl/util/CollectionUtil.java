package com.us.itp.odl.util;

import java.util.Collection;
import java.util.function.Predicate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public final class CollectionUtil {

    private CollectionUtil() {
        throw new AssertionError(
                "CollectionUtil is a static utility class and cannot be instantiated"
        );
    }

    @Nullable public static <E> E getFirst(
            @NonNull final Collection<E> coll,
            @NonNull final Predicate<E> pred
    ) {
        return coll.stream().filter(pred).findFirst().orElse(null);
    }
}
