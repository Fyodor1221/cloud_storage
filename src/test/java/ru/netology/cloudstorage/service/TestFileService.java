package ru.netology.cloudstorage.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import ru.netology.cloudstorage.entity.StorageFile;
import ru.netology.cloudstorage.exception.InvalidCredentials;
import ru.netology.cloudstorage.repository.FileRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class TestFileService {
    FileRepository mockedRepository = Mockito.mock(FileRepository.class);
    FileService service = new FileService(mockedRepository);
    String FILENAME = "filename";
    byte[] data = new byte[5];
    MockMultipartFile mockMultipartFile = new MockMultipartFile(FILENAME, data);

    /**
     * Проверка загрузки уже существующего файла
     */
    @Test
    void uploadFileExist() {
        Mockito.when(mockedRepository.findStorageFileByFileName(FILENAME))
                .thenReturn(Optional.of(new StorageFile()));

        Assertions.assertThrowsExactly(
                InvalidCredentials.class,
                () -> service.uploadFile(FILENAME, mockMultipartFile),
                "Filename is already taken");
    }

    /**
     * Проверка загрузки пустого файла
     */
    @Test
    void uploadFileEmpty() {
        data = null;
        mockMultipartFile = new MockMultipartFile(FILENAME, data);
        Mockito.when(mockedRepository.findStorageFileByFileName(FILENAME))
                .thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(
                InvalidCredentials.class,
                () -> service.uploadFile(FILENAME, mockMultipartFile),
                "File must not be empty");
    }

    /**
     * Проверка создания Entity
     */
    @Test
    void uploadFile() {
        Mockito.when(mockedRepository.findStorageFileByFileName(FILENAME))
                .thenReturn(Optional.empty());

        Assertions.assertEquals(service.uploadFile(FILENAME, mockMultipartFile).getFileName(), FILENAME);
        Assertions.assertEquals(service.uploadFile(FILENAME, mockMultipartFile).getData(), data);
        Assertions.assertEquals(service.uploadFile(FILENAME, mockMultipartFile).getSize(), data.length);
    }

    /**
     * Проверка удаления отсутствующего файла
     */
    @Test
    void deleteFileNotFound() {
        Mockito.when(mockedRepository.findStorageFileByFileName(FILENAME))
                .thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(
                InvalidCredentials.class,
                () -> service.deleteFile(FILENAME),
                "File not found");
    }

    /**
     * Проверка получения отсутствующего файла
     */
    @Test
    void getFileNotFound() {
        Mockito.when(mockedRepository.findStorageFileByFileName(FILENAME))
                .thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(
                InvalidCredentials.class,
                () -> service.deleteFile(FILENAME),
                "File not found");
    }

    /**
     * Проверка получения содержимого файла
     */
    @Test
    void getFile() {
        Mockito.when(mockedRepository.findStorageFileByFileName(FILENAME))
                .thenReturn(Optional.of(StorageFile.builder().data(data).build()));

        Assertions.assertEquals(service.getFile(FILENAME), data);
    }

    /**
     * Проверка переименования отсутствующего файла
     */
    @Test
    void renameFileNotFound() {
        Mockito.when(mockedRepository.findStorageFileByFileName(FILENAME))
                .thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(
                InvalidCredentials.class,
                () -> service.deleteFile(FILENAME),
                "File not found");
    }

    /**
     * Проверка получения нового имени файла
     */
    @Test
    void renameFile() {
        Mockito.when(mockedRepository.findStorageFileByFileName(FILENAME))
                .thenReturn(Optional.of(StorageFile.builder().data(data).build()));

        Map<String, String> body = new HashMap<>();
        String newName = "newName";
        body.put("filename", "newName");

        Assertions.assertEquals(service.renameFile(FILENAME, body), newName);
    }

    /**
     * Проверка получения меньше 0 файлов
     */
    @Test
    void getFilesNegative() {
        Assertions.assertThrowsExactly(
                InvalidCredentials.class,
                () -> service.getFiles(0), "Number of requested items must be positive");
        Assertions.assertThrowsExactly(
                InvalidCredentials.class,
                () -> service.getFiles(-1), "Number of requested items must be positive");
    }
}
