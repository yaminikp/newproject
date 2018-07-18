package com.us.itp.odl.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Data
@ToString(exclude = "password")
public final class Customer implements User {

    @NonNull private String username;

    @Getter(AccessLevel.NONE)
    @NonNull private String password;

    @Setter(AccessLevel.PACKAGE)
    @NonNull private Name realName;
    private void setRealName(
            @NonNull final String firstName,
            @NonNull final String middleName,
            @NonNull final String lastName
    ) {
        setRealName(new Name(firstName, middleName, lastName));
    }

    @NonNull private String address;
    @NonNull private String email;
    @NonNull private String phoneNumber;
    @NonNull private String aadhaarCardNumber;

    public Customer(
            @NonNull final String username,
            @NonNull final String password,
            @NonNull final String firstName,
            @NonNull final String middleName,
            @NonNull final String lastName,
            @NonNull final String address,
            @NonNull final String email,
            @NonNull final String phoneNumber,
            @NonNull final String aadhaarCardNumber
    ) {
        this.username = username;
        this.password = password;
        setRealName(firstName, middleName, lastName);
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.aadhaarCardNumber = aadhaarCardNumber;
    }
}
