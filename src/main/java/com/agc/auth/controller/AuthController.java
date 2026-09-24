package com.agc.auth.controller;

import com.agc.auth.dto.AuthenticationDTO;
import com.agc.auth.dto.LoginResponseDTO;
import com.agc.auth.dto.RegisterDTO;
import com.agc.auth.model.User;
import com.agc.auth.security.TokenService;
import com.agc.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO data) {

        // Cria o token de autenticação
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        // Validação da senha
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Gera o JWT a partir do usuário validado
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        this.authService.register(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
