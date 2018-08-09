package com.us.itp.odl.controller;

import com.us.itp.odl.dto.OfficeDto;
import com.us.itp.odl.service.OfficeService;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class SuperadminController {

    @NonNull private OfficeService officeService;

    public SuperadminController(@NonNull final OfficeService officeService) {
        this.officeService = officeService;
    }

    @PostMapping("/superadmin/office")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOffice(@RequestBody @NonNull final OfficeDto dto) {
        officeService.saveOffice(dto.toOffice());
    }
}
