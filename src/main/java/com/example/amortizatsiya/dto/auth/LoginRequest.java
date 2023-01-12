package com.example.amortizatsiya.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * Created By sadullayevdiyorbek98@gmail.com
 * Date : 25.05.2022
 * Time : 14:42
 * Project name : uzAvtoSavdo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @JsonProperty("username")
    @NotEmpty(message = "Username cannot be null or empty!")
    private String username;
    @JsonProperty("password")
    @NotEmpty(message = "Password cannot be null or empty!")
    private String password;
}
