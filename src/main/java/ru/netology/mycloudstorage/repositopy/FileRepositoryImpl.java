package ru.netology.mycloudstorage.repositopy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.mycloudstorage.exeptions.ExceptionHandler;
import ru.netology.mycloudstorage.exeptions.InternalServerException;
import ru.netology.mycloudstorage.exeptions.MyNotFoundException;
import ru.netology.mycloudstorage.modele.File;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileRepositoryImpl {
    @Autowired
    private MyFileRepository myFileRepository;

    private FileManager fileManager = new FileManager();
    private final static int NOT_DELETED = 0;
    @PersistenceContext
    private EntityManager entityManager;

    public FileRepositoryImpl(FileManager fileManager, MyFileRepository myFileRepository) {
        this.fileManager = fileManager;
        this.myFileRepository = myFileRepository;
    }

    public ResponseEntity<Object> getList(int limit) {
        List<File> list = myFileRepository.findAllByDeleted(NOT_DELETED);
        if (limit <= 0) {
            return ResponseEntity.status(400).body("Error input data");
        }
        if (list.size() > 3) {
            return ResponseEntity.status(500).body("Error getting file list");
        }
        return ResponseEntity.status(200).body(list);
    }

    @Transactional
    public String addFile(String filename, long size, byte[] byteArr) {
        if (size <= 0 || byteArr.length == 0 || filename.length() == 0) {
            //400
            return "Error input data";
            }
        File file = myFileRepository.findByFilenameAndDeleted(filename, NOT_DELETED);
        if (file == null) {
            File downloadFile = File.builder()
                    .filename(filename)
                    .size((int) size)
                    .path(fileManager.getPrefixPath() + filename)
                    .build();
            entityManager.persist(downloadFile);
            if (downloadFile == null) {
                //500
                return "Error adding file";
            }
            fileManager.createFile(filename, byteArr);
        }
        return "Success upload";
    }

    @Transactional
    public String changeName(String oldName, String newName) {
        File file = myFileRepository.findByFilenameAndDeleted(oldName, NOT_DELETED);
        if (file == null) {
            //400
            return "Error input data";
        }
        String oldPath = file.getPath();
        if (!fileManager.changeName(newName, oldPath)) {
            //500
            return "Error upload file";
        }
        file.setFilename(newName);
        file.setPath(fileManager.getPrefixPath() + newName);
        entityManager.persist(file);
        //OK
        return "Success upload";
    }

    @Transactional
    public String deleteFile(String filename) {
        File file = myFileRepository.findByFilenameAndDeleted(filename, NOT_DELETED);
        if (file == null) {
            //400
            return "Error input data";
        }
        String path = file.getPath();
        if (!fileManager.delete(filename, path)) {
            //500
            return "Error delete file";
        }
        int DELETED = 1;
        file.setDeleted(DELETED);
        file.setPath(fileManager.getPrefixPath() + fileManager.getDeleted() + filename);
        entityManager.persist(file);
        return "Success deleted";
    }

    public Object getFile(String filename) throws IOException {
        File file = myFileRepository.findByFilenameAndDeleted(filename, NOT_DELETED);
        if (file == null) {
            //400
            return "Error input data";
        }
        byte[] fileBytes = fileManager.getFile(file);
        if (fileBytes.length == 0) {
            //500
            return "Error upload file";
        }
        return fileBytes;
    }
}

