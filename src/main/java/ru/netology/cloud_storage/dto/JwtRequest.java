package ru.netology.cloud_storage.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String login;

    private String password;
}
