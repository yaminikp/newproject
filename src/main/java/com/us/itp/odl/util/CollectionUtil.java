package com.us.itp.odl.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public final class CollectionUtil {

    private CollectionUtil() {
        StaticUtil.preventInstantiation(this.getClass());
    }

    @Nullable public static <E> E getFirst(
            @NonNull final Collection<E> coll,
            @NonNull final Predicate<E> pred
    ) {
        return coll.stream().filter(pred).findFirst().orElse(null);
    }

    @NonNull public static <E, R> List<R> mapToList(
            @NonNull final Collection<E> coll,
            @NonNull final Function<E, R> transform
    ) {
        return coll.stream().map(transform).collect(Collectors.toList());
    }

    @NonNull public static <E> Set<E> immutableSetOf(
            @NonNull final Collection<? extends E> baseCollection,
            @NonNull final Collection<? extends E> newElements
    ) {
        return Collections.unmodifiableSet(setOf(baseCollection, newElements));
    }

    @NonNull private static <E> Set<E> setOf(
            @NonNull final Collection<? extends E> baseCollection,
            @NonNull final Collection<? extends E> newElements
    ) {
        final Set<E> set = new HashSet<>(baseCollection);
        set.addAll(newElements);
        return set;
    }
}
