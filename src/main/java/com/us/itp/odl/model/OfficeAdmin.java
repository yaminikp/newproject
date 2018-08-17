package com.us.itp.odl.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
public class OfficeAdmin extends CommonUser {

    @ManyToOne @NonNull private Office office;

    public OfficeAdmin(
            @NonNull final Office office,
            @NonNull final String firstName,
            @NonNull final String middleName,
            @NonNull final String lastName,
            @NonNull final String gender,
            @NonNull final LocalDate dateOfBirth,
            @NonNull final Address address,
            @NonNull final String phoneNumber,
            @NonNull final String email,
            @NonNull final String username,
            @NonNull final String password
    ) {
        super(username, password,
                firstName, middleName, lastName,
                gender, dateOfBirth, address, phoneNumber, email
        );
        this.office = office;
    }
}
