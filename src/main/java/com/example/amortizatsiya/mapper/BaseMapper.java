package com.example.amortizatsiya.mapper;

import com.example.amortizatsiya.dto.BaseDto;
import com.example.amortizatsiya.model.BaseEntity;

import java.util.List;

public interface BaseMapper<E extends BaseEntity, D extends BaseDto> {
    D convertFromEntity(E entity);
    E convertFromDto(D dto);



    List<D> convertFromEntityList(List<E> entityList);

    List<E> convertFromDtoList(List<D> dtoList);
}
