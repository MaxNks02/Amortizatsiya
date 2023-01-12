package com.example.amortizatsiya.mapper;

import com.example.amortizatsiya.dto.UserDto;
import com.example.amortizatsiya.security.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

/**
 * Created By sadullayevdiyorbek98@gmail.com
 * Date : 25.05.2022
 * Time : 11:47
 * Project name : uzAvtoSavdo
 */

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {RoleMapper.class})
@Component
public interface UserMapper extends BaseMapper<Users, UserDto> {
}
