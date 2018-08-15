package com.us.itp.odl.util;

/** Version of {@link java.util.function.Consumer} that is allowed to throw. */
@FunctionalInterface
public interface Assertion<T> {
    void check(T input) throws Exception;
}
