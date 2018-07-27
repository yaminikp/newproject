package com.us.itp.odl.model;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
final class Name {
    @NonNull private String firstName;
    @NonNull private String middleName;
    @NonNull private String lastName;
}
