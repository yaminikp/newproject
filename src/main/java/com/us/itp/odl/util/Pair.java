package com.us.itp.odl.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("WeakerAccess")
public class Pair<A, B> {

    @Nullable private final A first;
    @Nullable private final B second;

    static <A, B> Pair<A, B> of(@Nullable A first, @Nullable B second) {
        return new Pair<>(first, second);
    }
}
