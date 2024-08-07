package ru.netology.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.netology.cloudstorage.entity.StorageFile;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<StorageFile, Long> {
    Optional<StorageFile> findStorageFileByFileName(String fileName);

    void deleteStorageFileByFileName(String fileName);

    @Modifying
    @Query("update StorageFile as sf set sf.fileName = :newFileName where sf.fileName = :id")
    void updateFileNameById(String id, String newFileName);
}
