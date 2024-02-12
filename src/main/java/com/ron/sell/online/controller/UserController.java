package com.ron.sell.online.controller;
// src/main/java/com/auth0/example/web/APIController.java

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ron.sell.online.aspectj.Authenticated;
import com.ron.sell.online.domain.Message;

@RestController
@RequestMapping(path = "user/authenticated/profile", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
@CrossOrigin(origins = "*")

public class UserController {

    @GetMapping
    @Authenticated
    public Message getUserProfile() {
        System.out.println(" this is user profile endpoint");
        return new Message("All good. You are logged in user. WIP to print your name ");
    }

}