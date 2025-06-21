package com.emr.slgi.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.auth.dto.RegisterByPatientDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/patient")
    public ResponseEntity<?> registerByPatient(@RequestBody RegisterByPatientDTO registerByPatient) {
        authService.registerByPatient(registerByPatient);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
