package com.us.itp.odl.dao;

import com.us.itp.odl.model.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface OfficeRepository extends JpaRepository<Office, Long> {

    @Query("SELECT office FROM Office office WHERE office.officeCode = ?1")
    @Nullable Office getByCode(@NonNull final String officeCode);
}
