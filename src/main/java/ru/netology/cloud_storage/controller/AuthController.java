package ru.netology.cloud_storage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.cloud_storage.dto.JwtRequest;
import ru.netology.cloud_storage.securityService.AuthenticationService;
import ru.netology.cloud_storage.token.JwtAuthenticationResponse;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody @Valid JwtRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader("auth-token") String token) {
        authenticationService.logout(token);
        return ResponseEntity.ok().build();
    }
}
