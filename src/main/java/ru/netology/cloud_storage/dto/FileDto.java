package ru.netology.cloud_storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class FileDto {
    private String name;
    private LocalDateTime editedAt;
    private long size;
}
