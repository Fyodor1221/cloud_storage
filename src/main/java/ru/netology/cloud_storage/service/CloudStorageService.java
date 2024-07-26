package ru.netology.cloud_storage.service;

import ru.netology.cloud_storage.entity.StorageFile;
import ru.netology.cloud_storage.exception.InvalidCredentials;
import ru.netology.cloud_storage.exception.ServerFail;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloud_storage.repository.CloudStorageRepository;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CloudStorageService {
    private CloudStorageRepository repository;

    public void uploadFile(String fileName, MultipartFile file) {
        if (repository.findStorageFileByFileName(fileName).isPresent()) {
            throw new InvalidCredentials("Filename is already taken");
        }
        if (file.isEmpty()) {
            throw new InvalidCredentials("File must not be empty");
        }
        long fileSize = file.getSize();
        StorageFile storageFile = new StorageFile();
        if (fileName == null) {
            storageFile.setFileName(file.getName());
        }
        storageFile.setFileName(fileName);
        storageFile.setSize(fileSize);
        try {
            storageFile.setData(file.getBytes());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(storageFile.getData());
            String hashString = new BigInteger(1, digest).toString(16);
            storageFile.setHashMd5(hashString);
        } catch (IOException e) {
            throw new ServerFail("Error upload file");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (storageFile.getData() != null) {
                repository.save(storageFile);
            }
        }
    }

    public void deleteFile(String fileName) {
        if (repository.findStorageFileByFileName(fileName).isEmpty()) {
            throw new InvalidCredentials("File not found");
        }
        repository.deleteStorageFileByFileName(fileName);
    }

    public byte[] getFile(String fileName) {
        Optional<StorageFile> storageFiles = repository.findStorageFileByFileName(fileName);
        if (storageFiles.isEmpty()) {
            throw new InvalidCredentials("File not found");
        }
        StorageFile storageFile = storageFiles.get();
        return storageFile.getData();
    }

    public void renameFile(String fileName, Map<String, String> map) {
        Optional<StorageFile> storageFiles = repository.findStorageFileByFileName(fileName);
        if (storageFiles.isEmpty()) {
            throw new InvalidCredentials("File not found");
        }
        String newFileName = map.get("name");
        repository.updateFileNameById(fileName, newFileName);
    }

    public Map<String, Long> getAllFiles (int limit) {
        if (limit <= 0) {
            throw new InvalidCredentials("Number of requested items must be positive");
        }
        Map<String, Long> allFiles = new HashMap<>();
        for (StorageFile sf : repository.findAll(PageRequest.ofSize(limit))) {
            allFiles.put(sf.getFileName(), sf.getSize());
        }
        return allFiles;
    }
}
