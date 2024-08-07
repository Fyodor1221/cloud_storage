package ru.netology.cloudstorage.configuration;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TokenBlackList {
    private final List<String> list = new ArrayList<>();

    public void addToken(String token) {
        list.add(token);
    }

    public boolean containsToken(String token) {
        return list.contains(token);
    }
}
