package com.us.itp.odl.model;

import com.us.itp.odl.util.StaticUtil;
import java.time.LocalDate;
import org.springframework.lang.NonNull;

public final class TestModels {

    private TestModels() {
        StaticUtil.preventInstantiation(this.getClass());
    }

    @NonNull public static Office getOffice() {
        return getOffice("TOF");
    }

    @NonNull public static Office getOffice(@NonNull final String officeCode) {
        return new Office(
                /* name = */ "Test Office",
                /* officeCode = */ officeCode,
                /* cityCode = */ "NWH",
                /* address = */ getAddress(),
                /* phoneNumber = */ "555-555-5555",
                /* email = */ "test-office@example.com"
        );
    }

    @NonNull public static Customer getCustomer() {
        return new Customer(
                /* realName = */ "Alice", "Mary", "Smith",
                /* gender = */ "female",
                /* dateOfBirth = */ LocalDate.of(2018, 10, 25),
                /* address = */ getAddress(),
                /* phoneNumber = */ "555-555-5555",
                /* email = */ "alice@example.com",
                /* password = */ "myPassword"
        );
    }

    @NonNull public static OfficeAdmin getOfficeAdmin() {
        return new OfficeAdmin(
                /* office = */ getOffice(),
                /* realName = */ "Bob", "", "Howard",
                /* gender = */ "male",
                /* dateOfBirth = */ LocalDate.of(1978, 4, 18),
                /* address = */ getAddress(),
                /* phoneNumber = */ "555-555-0123",
                /* email = */ "bob@example.com",
                /* password = */ "sw0rdF!sh"
        );
    }

    @NonNull private static Address getAddress() {
        return new Address(
                /* address1 = */ "123 Main St.",
                /* address2 = */ "",
                /* city = */ "Nowhere",
                /* postalCode = */ "55555",
                /* state = */ "MN",
                /* country = */ "USA"
        );
    }
}
