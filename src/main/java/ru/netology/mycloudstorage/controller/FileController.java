package ru.netology.mycloudstorage.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.mycloudstorage.dto.FileNameDTO;
import ru.netology.mycloudstorage.service.FileService;

import javax.ws.rs.Consumes;
import java.io.IOException;

@RestController
@CrossOrigin
@Slf4j
public class FileController {
    @Autowired
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/list")
    public ResponseEntity getList(@RequestParam int limit) {
        return fileService.getList(limit);
    }

    @PostMapping("/file")
    @Consumes(value = "multipart/form-data")
    public ResponseEntity addFile(@RequestParam String filename, @RequestBody MultipartFile file) throws IOException {
        log.info("add file " + filename);
        return fileService.addFile(filename, file.getSize(), file.getBytes());
    }

    @PutMapping("/file")
    @Consumes(value = "application/json")
    public ResponseEntity changeName(@RequestParam String filename, @RequestBody FileNameDTO newName) {
        log.info("change name from " + filename + " to " + newName);
        return fileService.changeName(filename, newName.getFilename());
    }

    @DeleteMapping("/file")
    public ResponseEntity deleteFile(@RequestParam String filename) {
        log.info("delete file " + filename);
        return fileService.deleteFile(filename);
    }

    @GetMapping("/file")
    public ResponseEntity get(@RequestParam String filename) throws IOException {
        log.info("download file " + filename);
        return fileService.getFile(filename);
    }
}

