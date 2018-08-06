package com.us.itp.odl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.us.itp.odl.model.Customer;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CustomerDto implements Serializable {

    @NonNull private String password;
    @NotBlank @NonNull private String firstName;
    @NonNull private String middleName;
    @NotBlank @NonNull private String lastName;
    @NonNull private String gender;
    @JsonFormat(pattern = "dd/MM/yyyy") @NonNull private LocalDate dateOfBirth;
    @NonNull private String address1;
    @NonNull private String address2;
    @NonNull private String city;
    @NonNull private String postalCode;
    @NonNull private String state;
    @NonNull private String country;
    @NonNull private String email;
    @NonNull private String phoneNumber;

    @NonNull public Customer toCustomer() {
        return new Customer(
                /* password = */ password,
                /* firstName = */ firstName,
                /* middleName = */ middleName,
                /* lastName = */ lastName,
                /* gender = */ gender,
                /* dateOfBirth = */ dateOfBirth,
                /* address1 = */ address1,
                /* address2 = */ address2,
                /* city = */ city,
                /* pinCode */ postalCode,
                /* state = */ state,
                /* country = */ country,
                /* email = */ email,
                /* phoneNumber = */ phoneNumber
        );
    }
}
