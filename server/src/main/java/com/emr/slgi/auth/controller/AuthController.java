package com.emr.slgi.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emr.slgi.auth.dto.LoginRequest;
import com.emr.slgi.auth.dto.LogoutRequest;
import com.emr.slgi.auth.dto.RefreshTokenRequest;
import com.emr.slgi.auth.dto.RegisterByPatientRequest;
import com.emr.slgi.auth.dto.TokenResponse;
import com.emr.slgi.auth.dto.UseridExistsResponse;
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
    public ResponseEntity<Void> registerByPatient(@RequestBody @Valid RegisterByPatientRequest registerByPatientRequest) {
        authService.registerByPatient(registerByPatientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/check-id")
    public ResponseEntity<UseridExistsResponse> checkIdDuplicate(
        @RequestParam("userid")
        @NotBlank
        @Pattern(regexp = RegexPatterns.USERID)
        String userid
    ) {
        return ResponseEntity.ok(authService.checkIdDuplicate(userid));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return ResponseEntity.noContent().build();
    }

}
