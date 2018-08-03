package com.us.itp.odl.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@Entity
public final class Office implements OdlEntity {

    @Id @GeneratedValue private long id;

    @NonNull private String name;
    @NonNull private String officeCode;
    @NonNull private String cityCode;
    @NonNull private Address address;
    @NonNull private String phoneNumber;
    @NonNull private String email;

    public Office(
            @NonNull final String name,
            @NonNull final String officeCode,
            @NonNull final String cityCode,
            @NonNull final String address1,
            @NonNull final String address2,
            @NonNull final String city,
            @NonNull final String postalCode,
            @NonNull final String state,
            @NonNull final String country,
            @NonNull final String phoneNumber,
            @NonNull final String email
    ) {
        this.name = name;
        this.officeCode = officeCode;
        this.cityCode = cityCode;
        this.address = new Address(address1, address2, city, postalCode, state, country);
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
