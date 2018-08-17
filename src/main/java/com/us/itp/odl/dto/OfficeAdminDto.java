package com.us.itp.odl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.us.itp.odl.model.Office;
import com.us.itp.odl.model.OfficeAdmin;
import com.us.itp.odl.validation.Gender;
import com.us.itp.odl.validation.Name;
import com.us.itp.odl.validation.Password;
import com.us.itp.odl.validation.PhoneNumber;
import com.us.itp.odl.validation.ThreeLetterCode;
import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public class OfficeAdminDto {

    @ThreeLetterCode @NonNull private String officeCode;

    @NotBlank @Name @NonNull private String firstName;
    @Name @NonNull private String middleName;
    @NotBlank @Name @NonNull private String lastName;

    @Gender @NonNull private String gender;
    @JsonFormat(pattern = "dd/MM/yyyy") @Past @NonNull private LocalDate dateOfBirth;

    @Valid @NonNull private AddressDto address;
    @PhoneNumber @NonNull private String phoneNumber;
    @NotBlank @Email @NonNull private String email;

    @NotBlank @NonNull private String username;
    @Password @NonNull private String password;

    @NonNull public OfficeAdmin toOfficeAdmin(@NonNull final Office office) {
        assert office.getOfficeCode().equals(officeCode);
        return new OfficeAdmin(
                office, firstName, middleName, lastName, gender, dateOfBirth,
                address.toAddress(), phoneNumber, email, username, password
        );
    }
}
