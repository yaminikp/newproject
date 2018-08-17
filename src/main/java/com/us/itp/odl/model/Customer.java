package com.us.itp.odl.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@NoArgsConstructor
@Entity
public class Customer extends CommonUser {

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
        super(/* username = */ email, password,
                firstName, middleName, lastName,
                gender, dateOfBirth, address, phoneNumber, email
        );
    }
}
