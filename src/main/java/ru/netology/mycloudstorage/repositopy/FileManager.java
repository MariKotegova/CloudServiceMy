package ru.netology.mycloudstorage.repositopy;

import lombok.Getter;
import ru.netology.mycloudstorage.modele.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class FileManager {
    private final String prefixPath = "C:\\Diplom\\";
    private final String deleted = "(deleted)";

    public void createFile(String filename, byte[] bytes) {
        java.io.File file = new java.io.File(prefixPath + filename);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean changeName(String newFilename, String path) {
        java.io.File oldFile = new java.io.File(path);
        if (!oldFile.exists()) {
            return false;
        }
        java.io.File file = new java.io.File(prefixPath + newFilename);
        return oldFile.renameTo(file);
    }

    public byte[] getFile(File file) throws IOException {
        Path path = Path.of(file.getPath());
        return Files.readAllBytes(path);
    }

    public boolean delete(String filename, String path) {
        java.io.File oldFile = new java.io.File(path);
        if (!oldFile.exists()) {
            return false;
        }
        java.io.File file = new java.io.File(prefixPath + deleted + filename);
        return oldFile.renameTo(file);
    }
}