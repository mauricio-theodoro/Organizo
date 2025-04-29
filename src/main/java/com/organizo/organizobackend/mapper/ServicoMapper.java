package com.organizo.organizobackend.mapper;

import com.organizo.organizobackend.dto.ServicoDTO;
import com.organizo.organizobackend.model.Servico;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServicoMapper {
    ServicoDTO toDto(Servico entity);
    Servico toEntity(ServicoDTO dto);
}