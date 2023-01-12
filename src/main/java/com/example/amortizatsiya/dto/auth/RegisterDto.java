package com.example.amortizatsiya.dto.auth;

import com.example.amortizatsiya.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

/**
 * Created By sadullayevdiyorbek98@gmail.com
 * Date : 26.05.2022
 * Time : 15:21
 * Project name : uzAvtoSavdo
 */

@ToString
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterDto extends BaseDto {
    @NotEmpty(message = "Username cannot be empty or null!")
    @JsonProperty("username")
    private String username;

    @NotEmpty(message = "Password cannot be empty or null!")
    @JsonProperty("password")
    private String password;


    @JsonProperty("is_enabled")
    private Boolean isEnabled = Boolean.TRUE;

    @NotEmpty(message = "User roles cannot be empty or null!")
    @JsonProperty("roles")
    private String roles;
}
