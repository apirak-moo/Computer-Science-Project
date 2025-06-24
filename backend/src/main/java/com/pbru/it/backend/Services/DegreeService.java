package com.pbru.it.backend.Services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbru.it.backend.DTO.DegreeDTO;
import com.pbru.it.backend.Models.Degree;
import com.pbru.it.backend.Models.Program;
import com.pbru.it.backend.Repositories.DegreeRepository;

@Service
public class DegreeService {
    
    @Autowired
    private DegreeRepository degreeRepository;

    public List<Degree> findAll() {
        return degreeRepository.findAll();
    }

    public Optional<Degree> findById(int id) {
        return degreeRepository.findById(id);
    }

    // public Degree save(Degree degree) {
    //     return degreeRepository.save(degree);
    // }

    public Degree save(DegreeDTO request) {
        Degree degree = new Degree();
        degree.setNameTh(request.getNameTh());
        degree.setNameEn(request.getNameEn());
        
        Set<Program> programs = request.getPrograms().stream().map(p -> {
            Program program  = new Program();
            program.setNameTh(p.getNameTh());
            program.setNameEn(p.getNameEn());
            program.setDegree(degree);
            return program;
        }).collect(Collectors.toSet());

        degree.setPrograms(programs);
        return degreeRepository.save(degree);
    }

    public Degree mapToDegree(DegreeDTO dto) {
        Degree degree = new Degree();
        degree.setNameTh(dto.getNameTh());
        degree.setNameEn(dto.getNameEn());

        Set<Program> programs = dto.getPrograms().stream().map(p -> {
            Program program = new Program();
            program.setNameTh(p.getNameTh());
            program.setNameEn(p.getNameEn());
            program.setDegree(degree); // ตั้งค่าความสัมพันธ์กลับ
            return program;
        }).collect(Collectors.toSet());

        degree.setPrograms(programs);
        return degree;
    }

}
