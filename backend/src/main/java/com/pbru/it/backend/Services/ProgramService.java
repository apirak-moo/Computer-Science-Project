package com.pbru.it.backend.Services;

import com.pbru.it.backend.Models.Program;
import com.pbru.it.backend.Repositories.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    public List<Program> findAll() {
        return programRepository.findAll();
    }

    public Optional<Program> findById(Long id) {
        return programRepository.findById(id);
    }

}
