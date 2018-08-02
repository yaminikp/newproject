package com.us.itp.odl.model;

import java.time.LocalDate;
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

    @Setter(AccessLevel.PACKAGE)
    @NonNull private Address address;
    private void setAddress(
            @NonNull final String address1,
            @NonNull final String address2,
            @NonNull final String city,
            @NonNull final String postalCode,
            @NonNull final String state,
            @NonNull final String country
    ) {
        setAddress(new Address(address1, address2, city, postalCode, state, country));
    }

    @NonNull private Gender gender;
    @NonNull private LocalDate dateOfBirth;
    @NonNull private String email;
    @NonNull private String phoneNumber;
    @NonNull private String aadhaarCardNumber;

    public Customer(
            @NonNull final String username,
            @NonNull final String password,
            @NonNull final String firstName,
            @NonNull final String middleName,
            @NonNull final String lastName,
            @NonNull final String gender,
            @NonNull final LocalDate dateOfBirth,
            @NonNull final String address1,
            @NonNull final String address2,
            @NonNull final String city,
            @NonNull final String postalCode,
            @NonNull final String state,
            @NonNull final String country,
            @NonNull final String email,
            @NonNull final String phoneNumber,
            @NonNull final String aadhaarCardNumber
    ) {
        super(username, password);
        setRealName(firstName, middleName, lastName);
        setAddress(address1, address2, city, postalCode, state, country);
        this.gender = Gender.of(gender);
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.aadhaarCardNumber = aadhaarCardNumber;
    }
}
