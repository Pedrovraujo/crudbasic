package com.example.demo.controller;

import com.example.demo.dto.CreateUserDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class CreateUserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> newUser(@RequestBody CreateUserDto createUserDto){
        return userService.newuser(createUserDto);
    }
}
