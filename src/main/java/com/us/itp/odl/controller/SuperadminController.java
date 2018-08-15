package com.us.itp.odl.controller;

import com.us.itp.odl.dto.OfficeDto;
import com.us.itp.odl.service.OfficeService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SuperadminController {

    @NonNull private OfficeService officeService;

    public SuperadminController(@NonNull final OfficeService officeService) {
        this.officeService = officeService;
    }

    @PostMapping("/superadmin/office")
    public ResponseEntity<Void> createOffice(
            @RequestBody @NonNull @Valid final OfficeDto dto,
            @NonNull final BindingResult binding
    ) {
        if (binding.hasErrors()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        officeService.saveOffice(dto.toOffice());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
