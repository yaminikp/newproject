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

    @NonNull private Gender gender;
    @NonNull private LocalDate dateOfBirth;

    @NonNull private Address address;
    @NonNull private String phoneNumber;

    @SuppressWarnings("unused")
    @NonNull public String getEmail() {
        return getUsername();
    }

    public Customer(
            @NonNull final String firstName,
            @NonNull final String middleName,
            @NonNull final String lastName,
            @NonNull final String gender,
            @NonNull final LocalDate dateOfBirth,
            @NonNull final Address address,
            @NonNull final String phoneNumber,
            @NonNull final String email,
            @NonNull final String password
    ) {
        super(email, password);
        setRealName(firstName, middleName, lastName);
        this.address = address;
        this.gender = Gender.of(gender);
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }
}
