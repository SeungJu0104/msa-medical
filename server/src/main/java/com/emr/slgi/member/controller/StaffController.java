package com.emr.slgi.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {
  private final MemberService memberService;

  @GetMapping("/list")
  public ResponseEntity<?> getStaffList(@AuthenticationPrincipal String uuid) {
    return ResponseEntity.ok(Map.of("list", memberService.getStaffList(uuid)));
  }
}
