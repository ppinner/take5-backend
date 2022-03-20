package com.example.take5.controller;

import com.example.take5.model.*;
import com.example.take5.repository.LoginRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.when;

@ContextConfiguration(locations = "/test-context.xml")
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

    //    create
    @Test
    void create_validId_returns201() {
        Login valid = new Login("username", "password", "id");

        when(loginRepository.findDistinctById("username")).thenReturn(null);

        ResponseEntity r = loginController.create(valid);
        assert (r.getStatusCode()).equals(HttpStatus.CREATED);
        //Test contents?
    }

    @Test
    void create_alreadyUsedId_returns400() {
        Login invalid = new Login("username", "password", "id");
        Login login = new Login("username", "pass2", "id2");

        when(loginRepository.findDistinctById("username")).thenReturn(login);

        assert (loginController.create(invalid).getStatusCode()).equals(HttpStatus.BAD_REQUEST);
    }

    @Test
    void create_nullInput_returns400() {
        assert (loginController.create(null).getStatusCode()).equals(HttpStatus.BAD_REQUEST);
    }

    @Test
    void create_wrongPassword_returns400() {
        Login invalid = new Login("username", "password1", "id");
        Login login = new Login("username", "realPass", "id");

        when(loginRepository.findDistinctById("username")).thenReturn(login);

        assert (loginController.create(invalid).getStatusCode()).equals(HttpStatus.BAD_REQUEST);
    }

    @Test
    void create_nullUsername_returns400() {
        Login invalid = new Login(null, "password", "id");

        assert (loginController.create(invalid).getStatusCode()).equals(HttpStatus.BAD_REQUEST);
    }

    @Test
    void create_nullPassword_returns400() {
        Login invalid = new Login("user", null, "id");

        assert (loginController.create(invalid).getStatusCode()).equals(HttpStatus.BAD_REQUEST);
    }

    @Test
    void create_nullId_returns400() {
        Login invalid = new Login("user", "pass", null);

        assert (loginController.create(invalid).getStatusCode()).equals(HttpStatus.BAD_REQUEST);
    }

    @Test
    void create_error_returns500() {
        Login valid = new Login("username", "password", "id");

        when(loginRepository.findDistinctById("username")).thenReturn(null);
        when(loginRepository.save(Mockito.any(Login.class))).thenThrow(new RuntimeException());

        assert (loginController.create(valid).getStatusCode()).equals(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void deleteLogin_validInput_returns204() {
        ResponseEntity<HttpStatus> response = loginController.deleteUserByUserId("21");

        assert (response.getStatusCode()).equals(HttpStatus.NO_CONTENT);
    }

}