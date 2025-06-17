package com.emr.slgi.doctor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.member.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final MemberService memberService;

    @GetMapping("/list")
    public ResponseEntity<?> getDoctorList() {
        Map<String, Object> map = new HashMap<>();
        map.put("list", memberService.getDoctorList());
        return ResponseEntity.ok().body(map);
    }
}
