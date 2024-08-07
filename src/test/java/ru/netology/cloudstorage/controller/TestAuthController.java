package ru.netology.cloudstorage.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.netology.cloudstorage.securityService.AuthenticationService;
import ru.netology.cloudstorage.securityService.JwtService;
import ru.netology.cloudstorage.service.UserService;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class TestAuthController {
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationService authenticationService;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void loginUnauthorized() throws Exception {
        Mockito.when(authenticationService.login("login", "password"))
                .thenReturn("token");

        mockMvc.perform(post("/login")
                        .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andExpect(status().isForbidden());
    }

    @Test
    void logoutUnauthorized() throws Exception {
        mockMvc.perform(post("/logout")
                        .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andExpect(status().isForbidden());
    }
}
