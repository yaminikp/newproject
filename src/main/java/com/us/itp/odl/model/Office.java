package com.us.itp.odl.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@Entity
public class Office implements OdlEntity {

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
            @NonNull final Address address,
            @NonNull final String phoneNumber,
            @NonNull final String email
    ) {
        this.name = name;
        this.officeCode = officeCode;
        this.cityCode = cityCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
