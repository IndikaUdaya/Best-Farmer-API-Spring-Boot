package com.indikaudaya.bestfarmer.controllers;

import com.indikaudaya.bestfarmer.entities.User;
import com.indikaudaya.bestfarmer.services.auth.AuthService;
import com.indikaudaya.bestfarmer.dto.SignupDTO;
import com.indikaudaya.bestfarmer.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupDTO signupDTO) {

        SignupDTO dto = new SignupDTO(signupDTO.getEmail(), signupDTO.getMobile(), signupDTO.getPassword(), signupDTO.getUserType(), signupDTO.isStatus());
        UserDTO createdUser = authService.createUser(dto);
       if (createdUser == null){
           return new ResponseEntity<>("User not created, come again later!", HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
