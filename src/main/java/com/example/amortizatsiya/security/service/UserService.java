package com.example.amortizatsiya.security.service;

import com.example.amortizatsiya.dto.UserDto;
import com.example.amortizatsiya.exception.CustomNotFoundException;
import com.example.amortizatsiya.exception.DatabaseException;
import com.example.amortizatsiya.exception.handler.ApiErrorMessages;
import com.example.amortizatsiya.exception.handler.ResponseHandler;
import com.example.amortizatsiya.mapper.UserMapper;
import com.example.amortizatsiya.repository.RoleRepo;
import com.example.amortizatsiya.repository.UserRepo;
import com.example.amortizatsiya.security.entity.Roles;
import com.example.amortizatsiya.security.entity.Users;
import com.example.amortizatsiya.security.entity.enums.RoleName;
import com.example.amortizatsiya.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class UserService extends BaseService<UserRepo, Users, UserDto, UserMapper> {

    private final RoleRepo roleRepo;

    protected UserService(UserRepo repository, @Qualifier("userMapperImpl") UserMapper mapper, RoleRepo roleRepo) {
        super(repository, mapper);
        this.roleRepo = roleRepo;
    }

    public Users findByUsername(String username) {
        Users user = getRepository().findByUsername(username);
        if (user == null)
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "User not found username: %s", username));
        return user;
    }

    public ResponseEntity<?> getAllUser(){
        List<Users> users = getRepository().findAll();
        if (users.isEmpty())
            return ResponseHandler.generateResponse((ApiErrorMessages.NOT_FOUND + "List is empty: %s"), HttpStatus.NOT_FOUND);
        List<UserDto> dtos = getMapper().convertFromEntityList(users);
        return ResponseHandler.generateResponse("Users list : ", HttpStatus.OK, dtos);
    }
    public UserDto saveUser(UserDto userDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String now = formatter.format(LocalDate.now());
        Roles roles = roleRepo.findByName(RoleName.USER);
        userDto.setRoles(Collections.singleton(roles));
        Users users = new Users();
        users.setUsername(userDto.getUsername());
        users.setPassword(userDto.getPassword());
        users.setRoles(Set.of(roles));
        users.setIsEnabled(Boolean.TRUE);

        try {
            getRepository().save(getMapper().convertFromDto(userDto));
        } catch (RuntimeException exception) {
            throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", exception.getMessage()));
        }

        return userDto;
    }

    public UserDto updateUser(UserDto userDto){
        Optional<Users> user = Optional.ofNullable(getRepository().findByUsername(userDto.getUsername()));
        if (user.isEmpty())
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "User not found : %s", user));

        try {
            getRepository().save(user.get());
        } catch (RuntimeException exception) {
            throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", exception.getMessage()));
        }
        return  userDto;
    }

    public void delete(Long id){
        Optional<Users> user = getRepository().findById(id);
        if (user.isEmpty())
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "User not found : %s", user));
        try {
            getRepository().deleteById(id);
        } catch (RuntimeException exception) {
            throw new DatabaseException(String.format(ApiErrorMessages.INTERNAL_SERVER_ERROR + " %s", exception.getMessage()));
        }
    }


    public Boolean existsUser(String username) {
        return Boolean.TRUE.equals(getRepository().existsByUsername(username));
    }




    @Override
    public UserDto update(UserDto dto, long id) {
        return null;
    }
}
