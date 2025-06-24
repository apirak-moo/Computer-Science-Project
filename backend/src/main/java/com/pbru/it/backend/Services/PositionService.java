package com.pbru.it.backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbru.it.backend.Models.Position;
import com.pbru.it.backend.Repositories.PositionRepository;

@Service
public class PositionService {
    
    @Autowired
    private PositionRepository positionRepository;

    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    public Optional<Position> findById(int id) {
        return positionRepository.findById(id);
    }

    public Position save(Position position) {

        return positionRepository.save(position);
    }

    public List<Position> saveAll(List<Position> positions) {
        return positionRepository.saveAll(positions);
    }

}
