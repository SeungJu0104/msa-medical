package com.emr.slgi.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final MemberService ms;

    @GetMapping("/getDoctorList")
    public ResponseEntity<Map<String, Object>> getDoctorList(){

        List<Member> doctorList = ms.getDoctorList();

        for (Member member : doctorList) {
            System.out.println(member.toString());
        }


        if(doctorList.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "다시 시도해주세요.");

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("doctorList", doctorList));

    }

//    @GetMapping("/registerReservationByPatient")
//    public ResponseEntity<Map<String, Object>> registerReservationByPatient(){
//
//
//
//
//        return null;
//    }



}
