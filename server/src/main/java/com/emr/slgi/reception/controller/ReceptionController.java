package com.emr.slgi.reception.controller;

import com.emr.slgi.reception.dto.ReceptionInfo;
import com.emr.slgi.reception.service.ReceptionService;
import com.emr.slgi.reception.service.WaitingList;
import com.emr.slgi.util.CommonErrorMessage;
import com.emr.slgi.util.ReceptionErrorMessage;
import com.emr.slgi.util.ReservationErrorMessage;
import com.emr.slgi.util.Validate;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reception")
public class ReceptionController {

    private final ReceptionService receptionService;

    @PostMapping
    public ResponseEntity<?> acceptPatientByStaff(@RequestBody @Valid ReceptionInfo receptionInfo) {

        if(receptionService.acceptPatientByStaff(receptionInfo) != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CommonErrorMessage.RETRY);
        }

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @GetMapping("/getWaitingList/{doctorUuid}")
    public ResponseEntity<Map<String, List<WaitingList>>> getWaitingList(
            @PathVariable("doctorUuid") String doctorUuid
    ) {

        List<WaitingList> list = receptionService.getWaitingList(doctorUuid);

        return ResponseEntity.ok(
                Map.of("waitingList", list)
        );

    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @GetMapping("/getDoctorName/{uuid}/{role}")
    public ResponseEntity<Map<String, String>> getDoctorName(
            @PathVariable("uuid") String uuid,
            @PathVariable("role") String role) {

        String name;

        if(Validate.regexValidate(Map.of(Validate.MEMBER_UUID_REGEX, uuid)).contains(false)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReceptionErrorMessage.CAN_NOT_FIND_DATA);
        }

        if(role == "R002") {
            name = receptionService.getDoctorNameByDoctor(uuid);
        }else {
            // String 대신 Map 사용?
            name = receptionService.getDoctorNameByNurse();
        }

        if(name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReceptionErrorMessage.CAN_NOT_FIND_DATA);
        }

        return ResponseEntity.ok(
                Map.of("name", name)
        );

    }

}
