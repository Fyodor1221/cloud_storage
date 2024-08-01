package ru.netology.cloud_storage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.cloud_storage.CloudStorageApplication;
import ru.netology.cloud_storage.dto.JwtRequest;
import ru.netology.cloud_storage.securityService.AuthenticationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final Logger logger = LoggerFactory.getLogger(CloudStorageApplication.class);

    @PostMapping("/login")
    public @ResponseBody Map<String, String> login(@RequestBody @Valid JwtRequest request) {
        String jwt = authenticationService.login(request.getLogin(), request.getPassword());
        Map<String, String> map = new HashMap<>();
        map.put("auth-token", jwt);
        logger.info("TOKEN: {}", jwt);
        return map;
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader("auth-token") String token) {
        authenticationService.logout(token);
        return ResponseEntity.ok().build();
    }
}
