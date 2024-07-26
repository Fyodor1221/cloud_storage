package ru.netology.cloud_storage.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloud_storage.service.CloudStorageService;

import java.util.Map;

@RestController
@Transactional
@AllArgsConstructor
public class CloudStorageController {
    private CloudStorageService service;

    @PostMapping(path = {"/file"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadFile(@RequestParam("filename") String fileName, @RequestParam MultipartFile file) {
        service.uploadFile(fileName, file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = {"/file"})
    public ResponseEntity<Object> deleteFile(@RequestParam("filename") String fileName) {
        service.deleteFile(fileName);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = {"/file"}, produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<byte[]> getFile(@RequestParam("filename") String fileName) {
        return new ResponseEntity<>(service.getFile(fileName), HttpStatus.OK);
    }

    @PutMapping(path = {"/file"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> renameFile(@RequestParam("filename") String fileName, @RequestBody Map<String, String> map) {
        service.renameFile(fileName, map);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = {"/list"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Map<String, Long> getAllFiles(@RequestParam("limit") int limit) {
        return service.getAllFiles(limit);
    }
}
