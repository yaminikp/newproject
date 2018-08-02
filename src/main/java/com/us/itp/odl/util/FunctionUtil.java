package com.us.itp.odl.util;

import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.lang.NonNull;

public final class FunctionUtil {

    private FunctionUtil() {
        StaticUtil.preventInstantiation(this.getClass());
    }

    /** Converts the given function into a builder-style function that returns its input. */
    public static <T> Function<T, T> apply(@NonNull final Consumer<T> function) {
        return input -> {
            function.accept(input);
            return input;
        };
    }
}
