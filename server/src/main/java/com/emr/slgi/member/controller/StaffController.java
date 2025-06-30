package com.emr.slgi.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.member.service.StaffService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {
  private final StaffService staffService;

  @GetMapping("/list/{uuid}")
  public ResponseEntity<?> getStaffList(@PathVariable("uuid") String uuid) {
    return ResponseEntity.ok(Map.of("list", staffService.getStaffList(uuid)));
  }
}
