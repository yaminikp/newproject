package com.us.itp.odl.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import org.springframework.lang.NonNull;

public final class DateUtil {

    private DateUtil() {
        StaticUtil.preventInstantiation(this.getClass());
    }

    @NonNull public static Function<LocalDate, String> dateFormatter(
            @NonNull final String dateFormat
    ) {
        return date -> date.format(DateTimeFormatter.ofPattern(dateFormat));
    }
}
