package com.example.amortizatsiya.dto;

import com.example.amortizatsiya.security.entity.Roles;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;


@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends BaseDto {

    @JsonProperty("username")
    String username;
    @JsonProperty("password")
    String password;
    @JsonProperty("is_enabled")
    Boolean isEnabled = Boolean.TRUE;
    @JsonProperty("roles")
    Set<Roles> roles;
}
