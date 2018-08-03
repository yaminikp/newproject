package com.us.itp.odl.service;

import com.us.itp.odl.model.Office;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface OfficeService {
    @Nullable Office lookupOffice(@NonNull String officeCode);
    void saveOffice(@NonNull Office office);
}
