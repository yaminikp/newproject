package com.us.itp.odl.model;

import com.us.itp.odl.util.StaticUtil;
import java.time.LocalDate;
import org.springframework.lang.NonNull;

public final class TestModels {

    private TestModels() {
        StaticUtil.preventInstantiation(this.getClass());
    }

    @NonNull public static Office getOffice() {
        return new Office(
                /* name = */ "Test Office",
                /* officeCode = */ "TOF",
                /* cityCode = */ "NWH",
                /* address1 = */ "123 Main St.",
                /* address2 = */ "",
                /* city = */ "Nowhere",
                /* postalCode = */ "55555",
                /* state = */ "MN",
                /* country = */ "USA",
                /* phoneNumber = */ "555-555-5555",
                /* email = */ "test-office@example.com"
        );
    }

    @NonNull public static Customer getCustomer() {
        return new Customer(
                /* password = */ "myPassword",
                /* realName = */ "Alice", "Mary", "Smith",
                /* gender = */ "female",
                /* dateOfBirth = */ LocalDate.of(2018, 10, 25),
                /* address1 = */ "123 Main Street",
                /* address2 = */ "Apt. 318",
                /* city = */ "Woodbury",
                /* pinCode */ "55125",
                /* state = */ "MN",
                /* country = */ "USA",
                /* email = */ "alice@example.com",
                /* phoneNumber = */ "555-555-5555"
        );
    }
}
