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
import com.emr.slgi.reception.dto.WaitingList;
import com.emr.slgi.reception.enums.ReceptionMessage;
import com.emr.slgi.reception.enums.ReceptionStatus;
import com.emr.slgi.reception.service.ReceptionService;
import com.emr.slgi.reception.util.ReceptionErrorMessage;
import com.emr.slgi.reception.vo.UpdateReceptionStatusForm;
import com.emr.slgi.treatment.TreatmentDAO;
import com.emr.slgi.util.CommonErrorMessage;
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
    private final TreatmentDAO treatmentDAO;

    @PostMapping("/acceptPatientByStaff")
    public ResponseEntity<?> acceptPatientByStaff(@RequestBody @Valid ReceptionInfo receptionInfo) {

        if(receptionService.acceptPatientByStaff(receptionInfo) != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CommonErrorMessage.RETRY);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();

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
    @PutMapping("/updateStatus")
    public ResponseEntity<Map<String, String>> updateReceptionStatus(@RequestBody @Valid UpdateReceptionStatusForm urs) {

        Map<String, String> message = new HashMap<>();
        Map<String, Boolean> statusChk = new HashMap<>();

        if(urs.getUuid() == null || urs.getDoctorUuid() == null || urs.getPatientUuid() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonErrorMessage.RETRY);
        }

        if(Validate.regexValidation(
                Map.of(
                        Validate.MEMBER_UUID_REGEX,
                        List.of(urs.getUuid(), urs.getDoctorUuid(), urs.getPatientUuid())
                )
        ).contains(false)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReceptionErrorMessage.RECEPTION_DATA_NOT_VALID);
        }

        log.info(urs.toString());

        // 나중에 상태에 맞춰 별도 함수로 분리하는 코드 리팩토링 수행하기
        if (ReceptionStatus.ON_TREATMENT.equals(ReceptionStatus.from(urs.getUpdateStatus()))) {

            if(receptionService.checkOnTreatmentStatusExists(urs.getDoctorUuid()) > 0) {

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReceptionErrorMessage.ON_TREATMENT_STATUS_ALREADY_EXISTS);
            }

            statusChk.put("RE03", true);
            message.put("message", ReceptionMessage.CHANGE_STATUS_ON_TREATMENT.getMessage());

        }else{
            statusChk.put("RE03", false);
        }

        if(ReceptionStatus.CANCEL_RECEPTION.equals(ReceptionStatus.from(urs.getUpdateStatus()))) {

            statusChk.put("RE02", true);
            message.put("message", ReceptionMessage.CANCEL_SUCCESS.getMessage());

        }else{
            statusChk.put("RE02", false);
        }

        Map<String, ?> result = receptionService.updateReceptionStatus(urs.getUuid(), urs.getUpdateStatus());

        if (!Objects.equals(result.get("updateRes"), 1)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CommonErrorMessage.UPDATE_ERR + CommonErrorMessage.RETRY);
        }

        if(statusChk.get("RE03")) {

            // 여기서 진료 테이블 내에 INSERT문 수행하도록 treatment 쪽 서비스 연결
        	treatmentDAO.insertTreatment(urs.getUuid());

        }
        log.info(result.get("updateRes").toString());

        messagingTemplate.convertAndSend("/sub/status", "{}");

        return ResponseEntity.ok(
            message
        );

    }

}
