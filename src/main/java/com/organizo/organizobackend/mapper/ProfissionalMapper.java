package com.organizo.organizobackend.mapper;

import com.organizo.organizobackend.dto.ProfissionalDTO;
import com.organizo.organizobackend.model.Profissional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        imports = { java.util.stream.Collectors.class }
)
public interface ProfissionalMapper {

    ProfissionalMapper INSTANCE = Mappers.getMapper(ProfissionalMapper.class);

    @Mapping(target = "salaoId", source = "salao.id")
    @Mapping(target = "servicoIds", expression = "java(entity.getServicos().stream().map(s -> s.getId()).collect(Collectors.toSet()))")
    // Mapeia enum diretamente
    @Mapping(target = "cargo", source = "cargo")
    ProfissionalDTO toDto(Profissional entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "salao", ignore = true)
    @Mapping(target = "servicos", ignore = true)
        // O campo cargo é mapeado automaticamente pelo MapStruct
    Profissional toEntity(ProfissionalDTO dto);
}
