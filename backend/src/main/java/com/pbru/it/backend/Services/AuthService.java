package com.pbru.it.backend.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pbru.it.backend.DTO.request.ProfessorRequest;
import com.pbru.it.backend.DTO.response.ProfessorResponse;
import com.pbru.it.backend.Models.Professor;
import com.pbru.it.backend.Repositories.ProfessorRepository;

@Service
public class AuthService {

    private final ProfessorRepository professorRepository;
    private final PasswordEncoder encoder;
    private final ProfessorService professorService;
    private final JWTService jwtService;

    public AuthService(ProfessorRepository professorRepository, PasswordEncoder encoder,
            ProfessorService professorService, JWTService jwtService) {
        this.professorRepository = professorRepository;
        this.encoder = encoder;
        this.professorService = professorService;
        this.jwtService = jwtService;
    }

    public ProfessorResponse register(ProfessorRequest request) {
        return professorService.createProfessor(request);
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
