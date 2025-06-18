package com.emr.slgi.patient;

import com.emr.slgi.member.MemberService;
import com.emr.slgi.patient.dto.ReservationForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final MemberService ms;

    @GetMapping("/reservation")
    public ResponseEntity<Map<String, String>> makeReservation(@Valid ReservationForm rf){



        return ResponseEntity.ok().body(Map.of("message", "예약됐습니다."));
    }



}
