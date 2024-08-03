package ru.netology.cloud_storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class FileDto {
    private long id;
    private String filename;
    private Date createdAt;
    private Date editedAt;
    private long size;
}
