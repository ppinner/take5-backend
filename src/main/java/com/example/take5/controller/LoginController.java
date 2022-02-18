package com.example.take5.controller;

import com.example.take5.model.Login;
import com.example.take5.model.UserPass;
import com.example.take5.repository.LoginRepository;
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
        Login userData = loginRepository.findDistinctById(userPass.getUsername());

        if (userData != null && (userData.getPassword().equals(userPass.getPassword()))) {
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } else if (userData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (!userData.getPassword().equals(userPass.getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
