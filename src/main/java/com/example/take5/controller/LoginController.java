package com.example.take5.controller;

import com.example.take5.model.Login;
import com.example.take5.model.UserPass;
import com.example.take5.repository.LoginRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @PostMapping("/")
    public ResponseEntity<Login> findOne(@RequestBody UserPass userPass) {
        if (userPass != null) {
            try {
                Login userData = loginRepository.findDistinctById(userPass.getUsername());

                if (userData != null && (userData.getPassword().equals(userPass.getPassword()))) {
                    return new ResponseEntity<>(userData, HttpStatus.OK);
                } else if (userData == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else if (!userData.getPassword().equals(userPass.getPassword())) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/create")
    public ResponseEntity<Login> create(@RequestBody Login login) {
        if (login != null && login.getId() != null && login.getPassword() != null && login.getUserId() != null) {
            Login userData = loginRepository.findDistinctById(login.getId());

            if (userData != null) {
                return new ResponseEntity<>(login, HttpStatus.BAD_REQUEST);
            }

            Login newLogin = new Login(login.getId(), login.getPassword(), login.getUserId());

            try {
                return new ResponseEntity<>(loginRepository.save(newLogin), HttpStatus.CREATED);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserByUserId(@PathVariable("id") String id) {
        loginRepository.deleteLoginByUserId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
