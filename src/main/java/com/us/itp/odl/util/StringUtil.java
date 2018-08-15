package com.us.itp.odl.util;

import org.springframework.lang.NonNull;

public final class StringUtil {

    private StringUtil() {
        StaticUtil.preventInstantiation(this.getClass());
    }

    @NonNull public static String repeat(@NonNull final String s, int times) {
        assert times >= 0;
        if (times == 0) return "";
        final StringBuilder sb = new StringBuilder(s);
        for (int i = 1; i < times; i++) { sb.append(s); }
        return sb.toString();
    }
}
