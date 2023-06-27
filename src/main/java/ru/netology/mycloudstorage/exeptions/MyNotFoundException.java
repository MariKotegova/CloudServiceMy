package ru.netology.mycloudstorage.exeptions;

public class MyNotFoundException extends Exception{
    public MyNotFoundException() {
        super("Error input data");
    }
}
