package com.us.itp.odl.dto;

import com.us.itp.odl.model.Address;
import com.us.itp.odl.validation.AlphanumericName;
import com.us.itp.odl.validation.Name;
import com.us.itp.odl.validation.PostalCode;
import com.us.itp.odl.validation.SingleLine;
import com.us.itp.odl.validation.Text;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public final class AddressDto implements Serializable {
    @NotBlank @SingleLine @Text @NonNull private String address1;
    @SingleLine @Text @NonNull private String address2;
    @NotBlank @AlphanumericName @NonNull private String city;
    @PostalCode @NonNull private String postalCode;
    @NotBlank @Name @NonNull private String state;
    @NotBlank @Name @NonNull private String country;

    @NonNull Address toAddress() {
        return new Address(address1, address2, city, postalCode, state, country);
    }
}
