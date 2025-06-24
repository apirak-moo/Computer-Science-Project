package com.pbru.it.backend.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pbru.it.backend.Models.Professor;
import com.pbru.it.backend.Repositories.ProfessorRepository;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    public Optional<Professor> findById(UUID id) {
        return professorRepository.findById(id);
    }

    public Professor save(Professor professor) {
        professor.setPassword(encoder.encode(professor.getPassword()));
        return professorRepository.save(professor);
    }

    public Professor updateById(UUID id, Professor professor) {
        if (professorRepository.existsById(id)) {
            professor.setId(id);
            return professorRepository.save(professor);
        }
        return null;
    }

    public void deleteById(UUID id) {
        professorRepository.deleteById(id);
    }

    // public String verify(Professor professor) {
    //     Authentication authentication = authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(professor.getEmail(), professor.getPassword()));
    //     if (authentication.isAuthenticated()) {
    //         return jwtService.generateToken(professor.getEmail());
    //     }
    //     return "Fail";
    // }

}
