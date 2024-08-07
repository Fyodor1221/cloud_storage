package ru.netology.cloudstorage.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.netology.cloudstorage.repository.UserRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TestUserService {

    UserRepository mockedUserRepository = Mockito.mock(UserRepository.class);
    UserService userService = new UserService(mockedUserRepository);
    String USERNAME = "username";

    /**
     * Проверка поиска несуществующего пользователя
     */
    @Test
    void getByUsernameEmpty() {
        Mockito.when(mockedUserRepository.findByUsername(USERNAME))
                .thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(UsernameNotFoundException.class,
                () -> userService.getByUserName(USERNAME));
    }
}
