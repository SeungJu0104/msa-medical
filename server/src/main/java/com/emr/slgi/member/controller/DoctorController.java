package com.emr.slgi.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final MemberService memberService;

    @GetMapping("/list")
    public ResponseEntity<?> getDoctorList() {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getDoctorList());
    }

}
