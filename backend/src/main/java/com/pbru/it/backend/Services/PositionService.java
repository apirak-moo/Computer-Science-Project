package com.pbru.it.backend.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbru.it.backend.DTO.PositionRequest;
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

    public Position save(PositionRequest request) {
        Position position = new Position();
        position.setNameTh(request.nameTh());
        position.setNameEn(request.nameEn());
        return positionRepository.save(position);
    }

    public List<Position> saveAll(List<PositionRequest> requests) {
        if (!requests.isEmpty()) {
            List<Position> positions = new ArrayList<>();
            for (PositionRequest positionRequest : requests) {
                Position position = new Position();                
                position.setNameTh(positionRequest.nameTh());
                position.setNameEn(positionRequest.nameEn());
                positions.add(position);
            }
            return positionRepository.saveAll(positions);
        }
        else{
            throw new RuntimeException("ไม่พบข้อมูล เพิ่มข้อมูลไม่สำเร็จ");
        }
    }

}
