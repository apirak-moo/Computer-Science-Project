package com.pbru.it.backend.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pbru.it.backend.Models.Professor;
import com.pbru.it.backend.Repositories.ProfessorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ProfessorRepository professorRepository;
    private final PasswordEncoder encoder;

    private final JWTService jwtService;

    public Professor register(Professor request) {

        if (professorRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        request.setPassword(encoder.encode(request.getPassword()));

        return professorRepository.save(request);

    }

    public String login(String email, String password) {
        Professor professor = professorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!encoder.matches(password, professor.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtService.generateToken(professor);
    }

}
