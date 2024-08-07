package ru.netology.cloudstorage.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.netology.cloudstorage.configuration.JwtAuthenticationFilter;
import ru.netology.cloudstorage.configuration.SecurityConfig;
import ru.netology.cloudstorage.dto.FileDto;
import ru.netology.cloudstorage.entity.StorageFile;
import ru.netology.cloudstorage.securityService.AuthenticationService;
import ru.netology.cloudstorage.securityService.JwtService;
import ru.netology.cloudstorage.service.FileService;
import ru.netology.cloudstorage.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(FileController.class)
//@EnableWebSecurity
@SpringBootTest(classes = {SecurityConfig.class, FileController.class, AuthController.class})
@AutoConfigureMockMvc
public class TestFileController {
    @MockBean
    private JwtService jwtService;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private UserService userService;

    @MockBean
    private FileService fileService;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

    String FILENAME = "filename.txt";
    byte[] data = new byte[5];
    MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME, data);

    @Test
    void uploadFileUnauthorized() throws Exception {
        Mockito.when(fileService.uploadFile(FILENAME, mockMultipartFile))
                .thenReturn(new StorageFile());

        mockMvc.perform(post("/file")
                        .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteFileUnauthorized() throws Exception {
        mockMvc.perform(delete("/file")
                        .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andExpect(status().isForbidden());
    }

    @Test
    void getFileUnauthorized() throws Exception {
        Mockito.when(fileService.getFile(FILENAME))
                .thenReturn(data);

        mockMvc.perform(get("/file")
                        .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getFileAuthorized() throws Exception {
        Mockito.when(fileService.getFile(FILENAME))
                .thenReturn(data);

        mockMvc.perform(get("/file")
                        .header("auth-token", "token")
                        .param("filename", "file.txt"))
                .andExpect(status().isOk());
    }

    @Test
    void renameFileUnauthorized() throws Exception {
        Mockito.when(fileService.renameFile(FILENAME, new HashMap<>()))
                .thenReturn("NewFileName");

        mockMvc.perform(put("/file")
                        .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andExpect(status().isForbidden());
    }

    @Test
    void getFilesUnauthorized() throws Exception {
        List<FileDto> fileDtoList = new ArrayList<>();
        fileDtoList.add(new FileDto(1L, "file1.txt", new Date(), new Date(), data.length));
        fileDtoList.add(new FileDto(2L, "file2.txt", new Date(), new Date(), data.length));
        fileDtoList.add(new FileDto(3L, "file3.txt", new Date(), new Date(), data.length));

        Mockito.when(fileService.getFiles(3))
                .thenReturn(fileDtoList);

        mockMvc.perform(get("/list")
                        .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andExpect(status().isUnauthorized());
    }
}
