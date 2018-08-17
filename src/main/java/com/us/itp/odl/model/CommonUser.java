package com.us.itp.odl.model;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/** Represents a typical ODL user, as opposed to the Superadmin. */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
abstract class CommonUser extends User {

    @Setter(AccessLevel.PACKAGE)
    @NonNull private Name realName;
    private void setRealName(
            @NonNull final String firstName,
            @NonNull final String middleName,
            @NonNull final String lastName
    ) {
        setRealName(new Name(firstName, middleName, lastName));
    }

    @NonNull private Gender gender;
    @NonNull private LocalDate dateOfBirth;

    @NonNull private Address address;
    @NonNull private String phoneNumber;
    @NonNull private String email;

    CommonUser(
            @Nullable final String username,
            @NonNull final String password,
            @NonNull final String firstName,
            @NonNull final String middleName,
            @NonNull final String lastName,
            @NonNull final String gender,
            @NonNull final LocalDate dateOfBirth,
            @NonNull final Address address,
            @NonNull final String phoneNumber,
            @NonNull final String email
    ) {
        super(username, password);
        setRealName(firstName, middleName, lastName);
        this.address = address;
        this.gender = Gender.of(gender);
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
