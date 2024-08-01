package ru.netology.cloud_storage.securityService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.netology.cloud_storage.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String login(String principal, String credentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, credentials));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(principal);

        return jwtService.generateToken(user);
    }

    public void logout(String token) {
        jwtService.blockToken(token);
    }
}
