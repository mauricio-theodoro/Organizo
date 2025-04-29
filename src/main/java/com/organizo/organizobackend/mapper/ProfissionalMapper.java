package com.organizo.organizobackend.mapper;

import com.organizo.organizobackend.dto.ProfissionalDTO;
import com.organizo.organizobackend.model.Profissional;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfissionalMapper {
    ProfissionalDTO toDto(Profissional entity);
    Profissional toEntity(ProfissionalDTO dto);
}
