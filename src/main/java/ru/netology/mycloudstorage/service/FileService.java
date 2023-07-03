package ru.netology.mycloudstorage.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.netology.mycloudstorage.repositopy.FileRepositoryImpl;

import java.io.IOException;

@Service
public class FileService {
    private final FileRepositoryImpl fileRepository;

    public FileService(FileRepositoryImpl fileRepository) {
        this.fileRepository = fileRepository;
    }

    public ResponseEntity<Object> getList(int limit) {
        return fileRepository.getList(limit);
    }

    public String addFile(String filename, long size, byte[] byteArr) {
        return fileRepository.addFile(filename, size, byteArr);
    }

    public String changeName(String filename, String newName) {
        return fileRepository.changeName(filename, newName);
    }

    public String deleteFile(String filename) {
        return fileRepository.deleteFile(filename);
    }

    public Object getFile(String filename) throws IOException {
        return fileRepository.getFile(filename);
    }
}
