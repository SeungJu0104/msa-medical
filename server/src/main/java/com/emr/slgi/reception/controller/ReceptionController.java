package com.emr.slgi.reception.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.emr.slgi.reception.dto.ReceptionInfo;
import com.emr.slgi.reception.dto.ReceptionStatusList;
import com.emr.slgi.reception.dto.WaitingList;
import com.emr.slgi.reception.enums.ReceptionStatus;
import com.emr.slgi.reception.service.ReceptionService;
import com.emr.slgi.treatment.TreatmentService;
import com.emr.slgi.util.CommonErrorMessage;
import com.emr.slgi.util.ReceptionMessage;
import com.emr.slgi.util.Validate;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reception")
public class ReceptionController {

    private final ReceptionService receptionService;
    private final SimpMessageSendingOperations messagingTemplate;
    private final TreatmentService treatmentService;

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
    	
    	log.info(doctorUuid);
    	
        List<WaitingList> list = receptionService.getWaitingList(doctorUuid);
        

        log.info("대기목록 : {}", list.toString());

        return ResponseEntity.ok(
                Map.of("waitingList", list)
        );

    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @GetMapping("/statusList")
    public ResponseEntity<Map<String, List<ReceptionStatusList>>> statusList() {

        List<ReceptionStatusList> list = receptionService.getReceptionStatusList().get();

        if(list == null || list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonErrorMessage.RETRY);
        };

        return ResponseEntity.ok(
                Map.of("statusList", list)
        );

    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @PutMapping("/{patient}/{updateStatus}/updateStatus")
    public ResponseEntity<Map<String, String>> updateReceptionStatus(
            @PathVariable("patient") String uuid,
            @PathVariable("updateStatus") String updateStatus) {
    	
        Map<String, String> message = new HashMap<>();
        log.info("uuid ={}",uuid);
        log.info("uuid ={}",updateStatus);
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
        
        messagingTemplate.convertAndSend("/sub/status", "{}");
//        treatmentService.insertTreatment(patientUuid,doctorUuid);

        return ResponseEntity.ok(
            message
        );

    }

}
