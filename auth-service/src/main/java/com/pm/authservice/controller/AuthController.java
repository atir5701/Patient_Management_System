package com.pm.authservice.controller;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){this.authService=authService;}

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        Optional<String> token = authService.authenticate(loginRequestDTO);
        if(token.isEmpty()){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        LoginResponseDTO response = new LoginResponseDTO(token.get());
        return ResponseEntity.ok(response);
    }

}
