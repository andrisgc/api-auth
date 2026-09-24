package com.agc.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<String> getMyProfile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userEmail = authentication.getName();

        return ResponseEntity.ok("Acesso liberado. Você está logado como: " + userEmail);
    }

}
