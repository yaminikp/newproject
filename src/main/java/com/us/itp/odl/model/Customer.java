package com.us.itp.odl.model;

import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
public final class Customer extends User {

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
        super(username, password);
        setRealName(firstName, middleName, lastName);
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.aadhaarCardNumber = aadhaarCardNumber;
    }
}
