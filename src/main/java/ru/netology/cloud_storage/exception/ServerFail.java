package ru.netology.cloud_storage.exception;

public class ServerFail extends RuntimeException {
    public ServerFail(String msg) {
        super(msg);
    }
}
