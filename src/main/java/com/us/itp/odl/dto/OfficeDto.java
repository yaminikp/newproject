package com.us.itp.odl.dto;

import com.us.itp.odl.model.Office;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class OfficeDto implements Serializable {

    @NonNull private String name;
    @NonNull private String officeCode;
    @NonNull private String cityCode;
    @NonNull private String address1;
    @NonNull private String address2;
    @NonNull private String city;
    @NonNull private String postalCode;
    @NonNull private String state;
    @NonNull private String country;
    @NonNull private String phoneNumber;
    @NonNull private String email;

    @NonNull public Office toOffice() {
        return new Office(name, officeCode, cityCode,
                address1, address2, city, postalCode, state, country,
                phoneNumber, email
        );
    }
}
