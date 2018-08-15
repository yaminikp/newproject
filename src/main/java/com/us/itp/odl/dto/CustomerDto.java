package com.us.itp.odl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.us.itp.odl.model.Customer;
import com.us.itp.odl.validation.Gender;
import com.us.itp.odl.validation.Name;
import com.us.itp.odl.validation.Password;
import com.us.itp.odl.validation.PhoneNumber;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CustomerDto implements Serializable {

    @NotBlank @Name @NonNull private String firstName;
    @NonNull @Name private String middleName;
    @NotBlank @Name @NonNull private String lastName;

    @Gender @NonNull private String gender;
    @JsonFormat(pattern = "dd/MM/yyyy") @Past @NonNull private LocalDate dateOfBirth;

    @Valid @NonNull private AddressDto address;
    @PhoneNumber @NonNull private String phoneNumber;
    @NotBlank @Email @NonNull private String email;

    @Password @NonNull private String password;

    @NonNull public Customer toCustomer() {
        return new Customer(
                firstName, middleName, lastName, gender, dateOfBirth,
                address.toAddress(), phoneNumber, email, password
        );
    }
}
