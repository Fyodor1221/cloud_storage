package ru.netology.cloudstorage.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.dto.FileDto;
import ru.netology.cloudstorage.entity.StorageFile;
import ru.netology.cloudstorage.exception.InvalidCredentials;
import ru.netology.cloudstorage.exception.ServerFail;
import ru.netology.cloudstorage.repository.FileRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FileService {
    private FileRepository repository;

    public StorageFile uploadFile(String fileName, MultipartFile file) {
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
        } catch (IOException e) {
            throw new ServerFail("Error upload file");
        } finally {
            if (storageFile.getData() != null) {
                repository.save(storageFile);
            }
        }
        return storageFile;
    }

    public void deleteFile(String fileName) {
        if (repository.findStorageFileByFileName(fileName).isEmpty()) {
            throw new InvalidCredentials("File not found");
        }
        repository.deleteStorageFileByFileName(fileName);
    }

    public byte[] getFile(String fileName) {
        return repository.findStorageFileByFileName(fileName)
                .orElseThrow(() -> new InvalidCredentials("File not found"))
                .getData();
    }

    public String renameFile(String currentFileName, Map<String, String> body) {
        repository.findStorageFileByFileName(currentFileName)
                .orElseThrow(() -> new InvalidCredentials("File not found"));

        String newFileName = body.get("filename");
        repository.updateFileNameById(currentFileName, newFileName);
        return newFileName;
    }

    public List<FileDto> getFiles(int limit) {
        if (limit <= 0) {
            throw new InvalidCredentials("Number of requested items must be positive");
        }
        List<FileDto> files = new ArrayList<>();

        for (StorageFile sf : repository.findAll(PageRequest.ofSize(limit))) {
            files.add(new FileDto(
                    sf.getId(),
                    sf.getFileName(),
                    sf.getCreatedAt(),
                    sf.getEditedAt(),
                    sf.getSize()));
        }
        return files;
    }
}
