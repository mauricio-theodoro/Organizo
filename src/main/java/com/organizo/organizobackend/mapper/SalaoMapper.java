package com.organizo.organizobackend.mapper;

import com.organizo.organizobackend.dto.SalaoDTO;
import com.organizo.organizobackend.model.Salao;
import org.mapstruct.Mapper;

/**
 * Converte entidade Salao <-> DTO usando MapStruct.
 */
@Mapper(componentModel = "spring")
public interface SalaoMapper {

    SalaoDTO toDto(Salao entity);
    Salao toEntity(SalaoDTO dto);
}