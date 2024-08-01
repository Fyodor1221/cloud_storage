package ru.netology.cloud_storage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "STORAGE_FILES")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "FILE_NAME", nullable = false, unique = true)
    private String fileName;

    @Column(name = "DATA", columnDefinition = "bytea")
    private byte[] data;

    @Column(name = "SIZE", nullable = false)
    private long size;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
}
