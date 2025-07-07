package com.pbru.it.backend.Mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.pbru.it.backend.DTO.response.PositionResponse;
import com.pbru.it.backend.DTO.response.ProfessorEducationalQualificationResponse;
import com.pbru.it.backend.DTO.response.ProfessorProfileResponse;
import com.pbru.it.backend.DTO.response.ProfessorResponse;
import com.pbru.it.backend.Models.Position;
import com.pbru.it.backend.Models.Professor;
import com.pbru.it.backend.Models.ProfessorEducationalQualification;
import com.pbru.it.backend.Models.ProfessorProfile;
import com.pbru.it.backend.Models.ProfessorTitle;
import com.pbru.it.backend.DTO.response.ProfessorTitleResponse;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {

    ProfessorResponse toProfessorResponse(Professor professor);

    ProfessorProfileResponse toProfessorProfileResponse(ProfessorProfile profile);

    PositionResponse toPositionResponse(Position position);

    List<ProfessorResponse> toProfessorResponseList(List<Professor> professors);

    ProfessorEducationalQualificationResponse toProfessorEducationalQualificationResponse(
            ProfessorEducationalQualification qualification);

    ProfessorTitleResponse toProfessorTitleResponse(ProfessorTitle title);

    List<ProfessorTitleResponse> toProfessorTitleResponseList(List<ProfessorTitle> titles);

}
