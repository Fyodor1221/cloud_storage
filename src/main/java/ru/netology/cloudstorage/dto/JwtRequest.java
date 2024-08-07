package ru.netology.cloudstorage.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String login;

    private String password;
}
