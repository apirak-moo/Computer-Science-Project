package com.pbru.it.backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pbru.it.backend.DTO.request.PositionRequest;
import com.pbru.it.backend.Models.Position;
import com.pbru.it.backend.Repositories.PositionRepository;

@Service
public class PositionService {
    
    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    public Optional<Position> findById(int id) {
        // Find a position by its ID
        return positionRepository.findById(id);
    }

    public Position savePosition(String name) {
        // Create a new Position object and set its name
        Position position = new Position();
        position.setName(name);
        // Save the position to the database
        return positionRepository.save(position);
    }

    public List<Position> saveAllPosition(List<PositionRequest> requests) {
        // Convert PositionRequest objects to Position objects
        List<Position> positions = requests.stream()
            .map(request -> {
                Position position = new Position();
                position.setName(request.name());
                return position;
            })
            .toList();
        return positionRepository.saveAll(positions);
    }

    public void deletePosition(int id) {
        // Delete a position by its ID
        positionRepository.deleteById(id);
    }

}
