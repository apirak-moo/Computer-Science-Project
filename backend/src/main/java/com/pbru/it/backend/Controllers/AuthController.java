package com.pbru.it.backend.Controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbru.it.backend.Models.Professor;
import com.pbru.it.backend.Services.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Professor request){
        Professor save = authService.register(request);
        URI location = URI.create("/professor/" + save.getId());
        return ResponseEntity.created(location).body(save);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Professor professor){
        String token = authService.login(professor.getEmail(), professor.getPassword());
        return ResponseEntity.ok(token);
    }

}
