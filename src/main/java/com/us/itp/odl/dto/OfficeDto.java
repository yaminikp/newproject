package com.us.itp.odl.dto;

import com.us.itp.odl.model.Office;
import com.us.itp.odl.validation.AlphanumericName;
import com.us.itp.odl.validation.Name;
import com.us.itp.odl.validation.PhoneNumber;
import com.us.itp.odl.validation.PostalCode;
import com.us.itp.odl.validation.SingleLine;
import com.us.itp.odl.validation.Text;
import com.us.itp.odl.validation.ThreeLetterCode;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class OfficeDto implements Serializable {

    @NotBlank @AlphanumericName @NonNull private String name;

    @ThreeLetterCode @NonNull private String officeCode;
    @ThreeLetterCode @NonNull private String cityCode;

    @NotBlank @SingleLine @Text @NonNull private String address1;
    @SingleLine @Text @NonNull private String address2;
    @NotBlank @AlphanumericName @NonNull private String city;
    @PostalCode @NonNull private String postalCode;
    @NotBlank @Name @NonNull private String state;
    @NotBlank @Name @NonNull private String country;

    @PhoneNumber @NonNull private String phoneNumber;
    @NotBlank @Email @NonNull private String email;

    @NonNull public Office toOffice() {
        return new Office(name, officeCode, cityCode,
                address1, address2, city, postalCode, state, country,
                phoneNumber, email
        );
    }
}
