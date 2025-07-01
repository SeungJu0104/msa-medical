package com.emr.slgi.reception.controller;

import com.emr.slgi.reception.dto.ReceptionInfo;
import com.emr.slgi.reception.enums.ReceptionStatus;
import com.emr.slgi.reception.service.ReceptionService;
import com.emr.slgi.reception.dto.WaitingList;
import com.emr.slgi.util.CommonErrorMessage;
import com.emr.slgi.reception.enums.ReceptionMessage;
import com.emr.slgi.util.Validate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reception")
public class ReceptionController {

    private final ReceptionService receptionService;

    @PostMapping("/acceptPatientByStaff")
    public ResponseEntity<?> acceptPatientByStaff(@RequestBody @Valid ReceptionInfo receptionInfo) {

        if(receptionService.acceptPatientByStaff(receptionInfo) != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CommonErrorMessage.RETRY);
        }

        return ResponseEntity.ok().build();

    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @GetMapping("/{doctorUuid}")
    public ResponseEntity<Map<String, List<WaitingList>>> getWaitingList(
            @PathVariable("doctorUuid") String doctorUuid
    ) {

        List<WaitingList> list = receptionService.getWaitingList(doctorUuid);

        log.info(list.toString());

        return ResponseEntity.ok(
                Map.of("waitingList", list)
        );

    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @PutMapping("/{uuid}/{updateStatus}/updateStatus")
    public ResponseEntity<Map<String, String>> updateReceptionStatus(
            @PathVariable("uuid") String uuid,
            @PathVariable("updateStatus") String updateStatus) {

        Map<String, String> message = new HashMap<>();

        if(uuid == null || Validate.regexValidate(Map.of(Validate.MEMBER_UUID_REGEX, uuid)).contains(false)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonErrorMessage.RETRY);
        }

        Map<String, ?> result = receptionService.updateReceptionStatus(uuid, updateStatus);

        if (!Objects.equals(result.get("updateRes"), 1)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CommonErrorMessage.UPDATE_ERR + CommonErrorMessage.RETRY);
        }

        ReceptionStatus status = (ReceptionStatus) (result.get("status"));
        if ("RE02".equals(status.getCode())) {
            message.put("message", ReceptionMessage.CANCEL_SUCCESS.getMessage());
        }

        log.info(result.get("updateRes").toString());

        return ResponseEntity.ok(
            message
        );

    }

}
