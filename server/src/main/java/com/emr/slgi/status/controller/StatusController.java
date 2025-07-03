package com.emr.slgi.status.controller;

import com.emr.slgi.reception.dto.ReceptionStatusList;
import com.emr.slgi.reservation.dto.ReservationStatusList;
import com.emr.slgi.status.service.StatusService;
import com.emr.slgi.util.CommonErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @GetMapping("/receptionList")
    public ResponseEntity<Map<String, List<ReceptionStatusList>>> getReceptionStatusList() {

        List<ReceptionStatusList> list = statusService.getReceptionStatusList();

        if(list == null || list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonErrorMessage.RETRY);
        };

        return ResponseEntity.ok(
                Map.of("statusList", list)
        );

    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    @GetMapping("/reservationList")
    public ResponseEntity<Map<String, List<ReservationStatusList>>> getReservationStatusList() {

        List<ReservationStatusList> list = statusService.getReservationStatusList();

        if(list == null || list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CommonErrorMessage.RETRY);
        };

        log.info(list.toString());

        return ResponseEntity.ok(
                Map.of("statusList", list)
        );

    }


}
