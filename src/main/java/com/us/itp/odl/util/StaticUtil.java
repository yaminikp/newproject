package com.us.itp.odl.util;

import org.springframework.lang.NonNull;

final class StaticUtil {

    private StaticUtil() {
        preventInstantiation(this.getClass());
    }

    static void preventInstantiation(@NonNull final Class utilClass) {
        throw new AssertionError(
                utilClass.getSimpleName() + " is a static utility class and cannot be instantiated"
        );
    }
}
