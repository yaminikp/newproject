package com.us.itp.odl.model;

import org.springframework.lang.NonNull;

enum Gender {
    MALE, FEMALE;

    @NonNull public static Gender of(@NonNull final String gender) {
        return Gender.valueOf(gender.toUpperCase());
    }
}
