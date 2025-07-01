package com.emr.slgi.auth.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.auth.dto.LoginDTO;
import com.emr.slgi.auth.dto.LogoutDTO;
import com.emr.slgi.auth.dto.RefreshTokenDTO;
import com.emr.slgi.auth.dto.RegisterByPatientDTO;
import com.emr.slgi.auth.service.AuthService;
import com.emr.slgi.common.constants.RegexPatterns;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/patient")
    public ResponseEntity<?> registerByPatient(@RequestBody @Valid RegisterByPatientDTO registerByPatient) {
        authService.registerByPatient(registerByPatient);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/check-id")
    public ResponseEntity<?> checkIdDuplicate(
        @RequestParam("userid")
        @NotBlank
        @Pattern(regexp = RegexPatterns.USERID)
        String userid
    ) {
        boolean exists = authService.checkIdDuplicate(userid);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO) {
        return ResponseEntity.ok(
            Map.of("accessToken", authService.refreshToken(refreshTokenDTO))
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody @Valid LogoutDTO logoutDTO) {
        authService.logout(logoutDTO);
        return ResponseEntity.noContent().build();
    }
}
