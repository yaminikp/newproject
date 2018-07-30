package com.us.itp.odl.dto;

import com.us.itp.odl.model.Customer;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CustomerDto implements Serializable {

    @NonNull private String username;
    @NonNull private String password;
    @NonNull private String firstName;
    @NonNull private String middleName;
    @NonNull private String lastName;
    @NonNull private String address;
    @NonNull private String email;
    @NonNull private String phoneNumber;
    @NonNull private String aadhaarCardNumber;

    @NonNull public Customer toCustomer() {
        return new Customer(
                /* username = */ username,
                /* password = */ password,
                /* firstName = */ firstName,
                /* middleName = */ middleName,
                /* lastName = */ lastName,
                /* address = */ address,
                /* email = */ email,
                /* phoneNumber = */ phoneNumber,
                /* aadhaarCardNumber = */ aadhaarCardNumber
        );
    }
}
