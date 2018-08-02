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
final class Address {
    @NonNull private String address1;
    @NonNull private String address2;
    @NonNull private String city;
    @NonNull private String postalCode;
    @NonNull private String state;
    @NonNull private String country;
}
