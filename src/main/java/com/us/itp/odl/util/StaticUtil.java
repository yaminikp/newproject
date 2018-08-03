package com.us.itp.odl.util;

import org.springframework.lang.NonNull;

public final class StaticUtil {

    private StaticUtil() {
        preventInstantiation(this.getClass());
    }

    public static void preventInstantiation(@NonNull final Class utilClass) {
        throw new AssertionError(
                utilClass.getSimpleName() + " is a static utility class and cannot be instantiated"
        );
    }
}
