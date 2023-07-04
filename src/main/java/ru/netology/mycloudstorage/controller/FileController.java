package ru.netology.mycloudstorage.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> getList(@RequestParam int limit) {
        return ResponseEntity.status(200).body(fileService.getList(limit));
    }

    @PostMapping("/file")
    @Consumes(value = "multipart/form-data")
    public ResponseEntity<String> addFile(@RequestParam String filename, @RequestBody MultipartFile file) throws IOException {
        log.info("add file " + filename);
        String messages = fileService.addFile(filename, file.getSize(), file.getBytes());
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PutMapping("/file")
    @Consumes(value = "application/json")
    public ResponseEntity<String> changeName(@RequestParam String filename, @RequestBody FileNameDTO newName) {
        log.info("change name from " + filename + " to " + newName);
        String messages = fileService.changeName(filename, newName.getFilename());
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestParam String filename) {
        log.info("delete file " + filename);
        String messages = fileService.deleteFile(filename);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> get(@RequestParam String filename) throws IOException {
        log.info("download file " + filename);
        byte[] messages = fileService.getFile(filename);
        return ResponseEntity.ok(messages);
    }
}

