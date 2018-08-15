package com.us.itp.odl.dto;

import com.us.itp.odl.model.Office;
import com.us.itp.odl.validation.AlphanumericName;
import com.us.itp.odl.validation.PhoneNumber;
import com.us.itp.odl.validation.ThreeLetterCode;
import java.io.Serializable;
import javax.validation.Valid;
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

    @Valid @NonNull private AddressDto address;

    @PhoneNumber @NonNull private String phoneNumber;
    @NotBlank @Email @NonNull private String email;

    @NonNull public Office toOffice() {
        return new Office(name, officeCode, cityCode, address.toAddress(), phoneNumber, email);
    }
}
