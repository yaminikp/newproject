package com.us.itp.odl.service;

import com.us.itp.odl.dao.OfficeRepository;
import com.us.itp.odl.model.Office;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public final class OfficeServiceImpl implements OfficeService {

    @NonNull private final OfficeRepository repo;

    OfficeServiceImpl(@NonNull final OfficeRepository repo) {
        this.repo = repo;
    }

    @Override
    @Nullable public Office lookupOffice(@NonNull final String officeCode) {
        return repo.getByCode(officeCode);
    }

    @Override
    public void saveOffice(@NonNull final Office office) {
        repo.save(office);
    }
}
