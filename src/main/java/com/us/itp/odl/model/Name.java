package com.us.itp.odl.model;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
final class Name {
    @NonNull private final String firstName;
    @NonNull private final String middleName;
    @NonNull private final String lastName;
}
