package com.example.take5.controller;

import com.example.take5.model.*;
import com.example.take5.repository.LoginRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

@SpringBootTest
class LoginControllerTest {

    @Mock
    private LoginRepository loginRepository;

    @InjectMocks
    private LoginController loginController = new LoginController();

    @Test
    void findOne_validId_returns200() {
        UserPass valid = new UserPass("username", "password");
        Login login = new Login("username", "password", "id");

        when(loginRepository.findDistinctById("username")).thenReturn(login);

        ResponseEntity r = loginController.findOne(valid);
        assert (r.getStatusCode()).equals(HttpStatus.OK);
        assert (r.getBody()).equals(login);
    }

    @Test
    void findOne_invalidId_returns404() {
        UserPass invalid = new UserPass("username", "password");

        when(loginRepository.findDistinctById("username")).thenReturn(null);

        assert (loginController.findOne(invalid).getStatusCode()).equals(HttpStatus.NOT_FOUND);
    }

    @Test
    void findOne_nullInput_returns400() {
        assert (loginController.findOne(null).getStatusCode()).equals(HttpStatus.BAD_REQUEST);
    }

    @Test
    void findOne_wrongPassword_returns400() {
        UserPass invalid = new UserPass("username", "password1");
        Login login = new Login("username", "realPass", "id");

        when(loginRepository.findDistinctById("username")).thenReturn(login);

        assert (loginController.findOne(invalid).getStatusCode()).equals(HttpStatus.BAD_REQUEST);
    }

    @Test
    void findOne_nullUser_returns400() {
        UserPass invalid = new UserPass("username", "password");

        when(loginRepository.findDistinctById("username")).thenReturn(null);

        assert (loginController.findOne(invalid).getStatusCode()).equals(HttpStatus.NOT_FOUND);
    }

    @Test
    void findOne_error_returns500() {
        UserPass valid = new UserPass("username", "password");

        when(loginRepository.findDistinctById("username")).thenThrow(new RuntimeException());

        assert (loginController.findOne(valid).getStatusCode()).equals(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}