package com.emr.slgi.doctor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emr.slgi.member.Member;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.member.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final MemberService memberService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, List<Member>>> getDoctorList() {
        List<Member> list = memberService.getDoctorList();
        if(list.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "다시 시도해주세요.");
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("list", list));
    }

    @GetMapping("/{uuid}/name")
    public ResponseEntity<?> getDoctorName(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok().body(Map.of("name", memberService.getDoctorName(uuid)));
    }
}
