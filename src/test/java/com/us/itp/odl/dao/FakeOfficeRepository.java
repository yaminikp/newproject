package com.us.itp.odl.dao;

import static com.us.itp.odl.util.CollectionUtil.getFirst;

import com.us.itp.odl.model.Office;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@NoRepositoryBean
public class FakeOfficeRepository extends FakeRepository<Office> implements OfficeRepository {

    @Override
    @Nullable public Office getByCode(@NonNull final String officeCode) {
        return getFirst(db.values(), office -> office.getOfficeCode().equals(officeCode));
    }
}
