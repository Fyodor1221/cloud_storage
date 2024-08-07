package ru.netology.cloudstorage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.cloudstorage.CloudStorageApplication;
import ru.netology.cloudstorage.dto.JwtRequest;
import ru.netology.cloudstorage.securityService.AuthenticationService;

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
        logger.debug("TOKEN: {}", jwt);
        return map;
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader("auth-token") String token) {
        authenticationService.logout(token);
        return ResponseEntity.ok().build();
    }
}
