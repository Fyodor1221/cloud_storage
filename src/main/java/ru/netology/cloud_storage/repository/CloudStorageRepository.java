package ru.netology.cloud_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.netology.cloud_storage.entity.StorageFile;

import java.util.Optional;

@Repository
public interface CloudStorageRepository extends JpaRepository<StorageFile, Long> {
    Optional<StorageFile> findStorageFileByFileName(String fileName);

    void deleteStorageFileByFileName(String fileName);

    @Modifying
    @Query("update StorageFile as sf set sf.fileName = :newFileName where sf.fileName = :id")
    void updateFileNameById(String id, String newFileName);
}
