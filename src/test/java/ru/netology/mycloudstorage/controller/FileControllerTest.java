package ru.netology.mycloudstorage.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.netology.mycloudstorage.dto.FileNameDTO;
import ru.netology.mycloudstorage.modele.File;
import ru.netology.mycloudstorage.repositopy.FileManager;
import ru.netology.mycloudstorage.repositopy.FileRepositoryImpl;
import ru.netology.mycloudstorage.repositopy.MyFileRepository;
import ru.netology.mycloudstorage.service.FileService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileControllerTest {
    private final static String PREFIX_PATH = "C:\\Diplom\\";
    @Autowired
    private FileService fileService;
    private final static int NOT_DELETED = 0;

    @BeforeEach
    void start() {
        System.out.println("\n--Запуск теста--\n");
    }

    @AfterEach
    void end() {
        System.out.println("\n--Тест завершен--");
    }

    @Test
    void getListTest() {
        int limit = 3;
        int wrongLimit = 0;

        // OK
        System.out.println("ТЕСТ ОТОБРАЖЕНИЯ СПИСКА ФАЙЛОВ");
        System.out.println("Тестирование OK");
        FileController controller1 = new FileController(fileService);
        HttpStatus status = controller1.getList(limit).getStatusCode();
        HttpStatus status1 = HttpStatus.OK;
        System.out.println("Ожидалось получить - " + status1 + "\nПолучено - " + status);
        Assertions.assertEquals(status1, status);

        // BAD REQUEST
        System.out.println("\nТестирование BAD REQUEST");
        HttpStatus statusTest2 = null;
        try {
            statusTest2 = controller1.getList(wrongLimit).getStatusCode();
        } catch (ResponseStatusException e) {
            statusTest2 = e.getStatus();
        }
        HttpStatus status2 = HttpStatus.BAD_REQUEST;
        System.out.println("Ожидалось получить - " + status2 + "\nПолучено - " + statusTest2);
        Assertions.assertEquals(status2, statusTest2);

        // INTERNAL_SERVER_ERROR
        System.out.println("\nТестирование INTERNAL_SERVER_ERROR");
        List list = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            File myFile = File.builder()
                    .path(PREFIX_PATH + i + ".txt")
                    .filename(i + ".txt")
                    .build();
            list.add(myFile);
        }
        MyFileRepository myFileRepository3 = Mockito.mock(MyFileRepository.class);
        Mockito.when(myFileRepository3.findAllByDeleted(NOT_DELETED)).thenReturn(list);
        FileRepositoryImpl fileRepository3 = new FileRepositoryImpl(new FileManager(), myFileRepository3);
        FileService fileService3 = new FileService(fileRepository3);
        FileController controller3 = new FileController(fileService3);
        HttpStatus statusTest3 = null;
        try {
            statusTest3 = controller3.getList(limit).getStatusCode();
        } catch (ResponseStatusException e) {
            statusTest3 = e.getStatus();
        }
        HttpStatus status3 = HttpStatus.INTERNAL_SERVER_ERROR;
        System.out.println("Ожидалось получить - " + status3 + "\nПолучено - " + statusTest3);
        Assertions.assertEquals(status3, statusTest3);
    }

    @Test
    void changeNameTest() {
        System.out.println("ТЕСТ ИЗМЕНЕНИЯ ИМЕНИ ФАЙЛА");
        String oldName = "Name.txt";
        FileNameDTO fileName = new FileNameDTO();
        String newName = "newName.txt";
        fileName.setFilename(newName);
        System.out.println("Тестирование BAD REQUEST");

        //BAD REQUEST
        MyFileRepository myFileRepository1 = Mockito.mock(MyFileRepository.class);
        Mockito.when(myFileRepository1.findByFilenameAndDeleted(oldName, 0)).thenReturn(null);

        FileController testController1 = new FileController(new FileService(
                new FileRepositoryImpl(
                        new FileManager(), myFileRepository1)));

        HttpStatus testStatus1 = HttpStatus.BAD_REQUEST;
        HttpStatus status1 = null;
        try {
            status1 = testController1.changeName(oldName, fileName).getStatusCode();
        } catch (ResponseStatusException e) {
            status1 = e.getStatus();
        }
        System.out.println("Ожидалось получить - " + status1 + "\nПолучено - " + testStatus1);
        Assertions.assertEquals(status1, testStatus1);

        //ERROR UPLOAD FILE
        System.out.println("\nТестирование INTERNAL_SERVER_ERROR");
        File myFile = File.builder()
                .path(PREFIX_PATH + "name.txt")
                .filename("name.txt")
                .build();
        MyFileRepository myFileRepository2 = Mockito.mock(MyFileRepository.class);
        Mockito.when(myFileRepository2.findByFilenameAndDeleted(oldName, 0)).thenReturn(myFile);

        FileManager fileManager2 = Mockito.mock(FileManager.class);
        Mockito.when(fileManager2.changeName(oldName, newName)).thenReturn(false);

        FileController testController2 = new FileController(
                new FileService(
                        new FileRepositoryImpl(fileManager2, myFileRepository2)));

        HttpStatus testStatus2 = null;
        try {
            testStatus2 = testController2.changeName(oldName, fileName).getStatusCode();
        } catch (ResponseStatusException e) {
            testStatus2 = e.getStatus();
        }
        HttpStatus status2 = HttpStatus.INTERNAL_SERVER_ERROR;

        System.out.println("Ожидалось получить - " + status2 + "\nПолучено - " + testStatus2);
        Assertions.assertEquals(testStatus2, status2);
    }

    @Test
    void deleteFileTest() {
        System.out.println("ТЕСТ УДАЛЕНИЯ ФАЙЛА");
        System.out.println("Тестирование BAD REQUEST");
        // BAD REQUEST
        String oldName = "Name.txt";
        File myFile = File.builder()
                .path(PREFIX_PATH + "name.txt")
                .filename("name.txt")
                .build();

        MyFileRepository myFileRepository1 = Mockito.mock(MyFileRepository.class);
        Mockito.when(myFileRepository1.findByFilenameAndDeleted(oldName, NOT_DELETED)).thenReturn(null);

        FileController controller1 = new FileController(new FileService(
                new FileRepositoryImpl(
                        new FileManager(), myFileRepository1)));
        HttpStatus status1 = HttpStatus.BAD_REQUEST;
        HttpStatus testStatus1 = null;
        try {
            testStatus1 = controller1.deleteFile(oldName).getStatusCode();
        } catch (ResponseStatusException e) {
            testStatus1 = e.getStatus();
        }

        System.out.println("Ожидалось получить - " + status1 + "\nПолучено - " + testStatus1);
        Assertions.assertEquals(testStatus1, status1);

        //INTERNAL SERVER ERROR
        System.out.println("\nТестирование INTERNAL_SERVER_ERROR");
        FileManager fileManager2 = Mockito.mock(FileManager.class);
        Mockito.when(fileManager2.delete(oldName, PREFIX_PATH)).thenReturn(false);

        MyFileRepository myFileRepository2 = Mockito.mock(MyFileRepository.class);
        Mockito.when(myFileRepository2.findByFilenameAndDeleted(oldName, NOT_DELETED)).thenReturn(myFile);


        FileController controller2 = new FileController(
                new FileService(
                        new FileRepositoryImpl(fileManager2, myFileRepository2)));
        HttpStatus status2 = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpStatus testStatus2 = null;
        try {
            testStatus2 = controller2.deleteFile(oldName).getStatusCode();
        } catch (ResponseStatusException e) {
            testStatus2 = e.getStatus();
        }

        System.out.println("Ожидалось получить - " + status2 + "\nПолучено - " + testStatus2);
        Assertions.assertEquals(testStatus2, status2);
    }

    @Test
    void getFile() throws IOException {
        System.out.println("ТЕСТ СКАЧИВАНИЯ ФАЙЛА");
        System.out.println("Тестирование BAD REQUEST");
        byte[] emptyArr = "".getBytes();
        System.out.println("\nТестирование INTERNAL_SERVER_ERROR");
        File myFile = File.builder()
                .path(PREFIX_PATH + "name.txt")
                .filename("name.txt")
                .build();
        String oldName = "Name.txt";
        // BAD REQUEST

        MyFileRepository myFileRepository1 = Mockito.mock(MyFileRepository.class);
        Mockito.when(myFileRepository1.findByFilenameAndDeleted(oldName, NOT_DELETED)).thenReturn(null);

        FileController controller1 = new FileController(new FileService(
                new FileRepositoryImpl(
                        new FileManager(), myFileRepository1)));
        HttpStatus status1 = HttpStatus.BAD_REQUEST;
        HttpStatus testStatus1 = null;
        try {
            testStatus1 = controller1.deleteFile(oldName).getStatusCode();
        } catch (ResponseStatusException e) {
            testStatus1 = e.getStatus();
        }

        System.out.println("Ожидалось получить - " + status1 + "\nПолучено - " + testStatus1);
        Assertions.assertEquals(testStatus1, status1);

        //INTERNAL SERVER ERROR
        FileManager fileManager2 = Mockito.mock(FileManager.class);
        Mockito.when(fileManager2.getFile(myFile)).thenReturn(emptyArr);

        MyFileRepository myFileRepository2 = Mockito.mock(MyFileRepository.class);
        Mockito.when(myFileRepository2.findByFilenameAndDeleted(oldName, NOT_DELETED)).thenReturn(myFile);

        FileController controller2 = new FileController(new FileService(
                new FileRepositoryImpl(
                        fileManager2, myFileRepository2)));
        HttpStatus status2 = HttpStatus.INTERNAL_SERVER_ERROR;
        HttpStatus testStatus2 = null;
        try {
            testStatus2 = controller2.deleteFile(oldName).getStatusCode();
        } catch (ResponseStatusException e) {
            testStatus2 = e.getStatus();
        }

        System.out.println("Ожидалось получить - " + status2 + "\nПолучено - " + testStatus2);
        Assertions.assertEquals(testStatus2, status2);
    }
}
