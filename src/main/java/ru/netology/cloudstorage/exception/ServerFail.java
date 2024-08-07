package ru.netology.cloudstorage.exception;

public class ServerFail extends RuntimeException {
    public ServerFail(String msg) {
        super(msg);
    }
}
