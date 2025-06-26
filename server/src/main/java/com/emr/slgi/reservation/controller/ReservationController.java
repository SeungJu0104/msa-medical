package com.emr.slgi.reservation.controller;

import com.emr.slgi.reservation.dto.FindReservationDate;
import com.emr.slgi.reservation.dto.ReservationForm;
import com.emr.slgi.reservation.dto.ReservationList;
import com.emr.slgi.reservation.service.ReservationService;
import com.emr.slgi.util.CommonErrorMessage;
import com.emr.slgi.util.ReservationErrorMessage;
import com.emr.slgi.util.Validate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService rService;

    @PostMapping
    public ResponseEntity<Map<String, String>> makeReservationByPatient(@Valid @RequestBody ReservationForm rf){

        if(rService.makeReservation(rf) != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReservationErrorMessage.RESERVATION_RUN_ERROR + " " + CommonErrorMessage.RETRY);
        }

        return ResponseEntity.ok(
                Map.of("message", "예약이 완료됐습니다.")
        );

    }

    @PostMapping("/staff")
    public ResponseEntity<Map<String, String>> makeReservationByStaff(@Valid @RequestBody ReservationForm rf){

        if(rService.makeReservation(rf) != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReservationErrorMessage.RESERVATION_RUN_ERROR + " " + CommonErrorMessage.RETRY);
        }

        return ResponseEntity.ok(
                Map.of("message", "예약이 완료됐습니다.")
        );

    }

    @PostMapping("/hold")
    public ResponseEntity<?> holdReservation(FindReservationDate reservationDate) {

        if(reservationDate.getDoctorUuid() == null || Validate.regexValidate(Map.of(Validate.MEMBER_UUID_REGEX, reservationDate.getDoctorUuid())).contains(false)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReservationErrorMessage.CHOOSE_DOCTOR);
        }

        if(reservationDate.getDateTime() == null || reservationDate.getDateTime().toLocalDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReservationErrorMessage.CAN_NOT_FIND_RESERVATION_DATE);
        }

        if(rService.holdReservation(reservationDate) != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CommonErrorMessage.RETRY);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getReservationList/{doctorUuid}/{dateTime}")
    public ResponseEntity<Map<String, List<ReservationList>>> getReservationList(
            @PathVariable("doctorUuid") String doctorUuid,
            @PathVariable("dateTime")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime dateTime) {

        log.info(String.valueOf(dateTime));
        log.info(doctorUuid);

        FindReservationDate reservation = new FindReservationDate();



        if(doctorUuid == null || Validate.regexValidate(Map.of(Validate.MEMBER_UUID_REGEX, doctorUuid)).contains(false)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReservationErrorMessage.CHOOSE_DOCTOR);
        }

        // 왜 전날 날짜가 날아오지?
        if(dateTime == null || dateTime.toLocalDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReservationErrorMessage.CAN_NOT_FIND_RESERVATION_DATE);
        }

        reservation.setDateTime(dateTime);
        reservation.setDoctorUuid(doctorUuid);

        return ResponseEntity.ok(
                Map.of("reservationList", rService.getReservationList(reservation).orElse(List.of()))
        );

    }


    @GetMapping("/getReservationList/{dateTime}")
    public ResponseEntity<Map<String, List<ReservationList>>> getReservationList(@PathVariable("dateTime")
                                                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                                     OffsetDateTime dateTime) {
        if(dateTime == null || dateTime.toLocalDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReservationErrorMessage.CAN_NOT_FIND_RESERVATION_DATE);
        }

        return ResponseEntity.ok(
                Map.of("reservationList", rService.getReservationList(dateTime).orElse(List.of()))
        );

    }

    // 오버로딩으로 나중에 환자아이디로 예약 목록 가져오기 구현.
}
