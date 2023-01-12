package com.example.amortizatsiya.service;

import com.example.amortizatsiya.dto.BaseDto;
import com.example.amortizatsiya.exception.CustomNotFoundException;
import com.example.amortizatsiya.exception.DatabaseException;
import com.example.amortizatsiya.exception.handler.ApiErrorMessages;
import com.example.amortizatsiya.mapper.BaseMapper;
import com.example.amortizatsiya.model.BaseEntity;
import com.example.amortizatsiya.repository.BaseRepo;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public abstract class BaseService<R extends BaseRepo<E>, E extends BaseEntity, D extends BaseDto, M extends BaseMapper<E, D>> {
    private final R repository;
    private final M mapper;

    public BaseService(R repository, M mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public R getRepository() {
        return repository;
    }

    public M getMapper() {
        return mapper;
    }

    public List<D> getAll() {
        List<E> eList = repository.findAll();
        return entityListToDtoList(eList);
    }

    public D getById(long id) {
        E entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.convertFromEntity(entity);
    }

    @Transactional
    public E create(D dto) {
        E entity = mapper.convertFromDto(dto);
        try {
            return repository.save(entity);
        } catch (RuntimeException exception) {
            throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", exception.getMessage()));
        }
    }

    @Transactional
    public List<D> createAll(List<E> entityList) {
        try {
            return entityListToDtoList(repository.saveAll(entityList));
        } catch (RuntimeException exception) {
            throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", exception.getMessage()));
        }
    }

    public void deleteById(long id) {
        E entity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.deleteById(id);
    }

    public abstract D update(D dto, long id);

    public List<D> entityListToDtoList(List<E> eList) {
        if (eList.isEmpty()) {
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + " %s", "List empty!"));
        }
        return mapper.convertFromEntityList(eList);
    }
}
