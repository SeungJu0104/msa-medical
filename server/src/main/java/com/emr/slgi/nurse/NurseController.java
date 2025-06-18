package com.emr.slgi.nurse;

import com.emr.slgi.patient.dto.ReservationForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nurse")
public class NurseController {

    @GetMapping("/reservation")
    public ResponseEntity<Map<String, String>> makeReservation(@Valid ReservationForm rf){



        return ResponseEntity.ok().body(Map.of("message", "예약됐습니다."));
    }

}
