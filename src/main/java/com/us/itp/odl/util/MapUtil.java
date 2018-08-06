package com.us.itp.odl.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public final class MapUtil {

    private MapUtil() {
        StaticUtil.preventInstantiation(this.getClass());
    }

    @SafeVarargs @NonNull public static <K, V> Map<K, V> mapOf(
            @NonNull final Pair<K, V>... entries
    ) {
        final Map<K, V> map = new HashMap<>();
        for (Pair<K, V> entry : entries) {
            map.put(entry.getFirst(), entry.getSecond());
        }
        return map;
    }

    @NonNull public static <K, V> Pair<K, V> entry(@NonNull final K key, @Nullable final V value) {
        return Pair.of(key, value);
    }
}
