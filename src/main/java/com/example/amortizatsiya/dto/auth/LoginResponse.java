package com.example.amortizatsiya.dto.auth;

import com.example.amortizatsiya.security.entity.Users;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created By sadullayevdiyorbek98@gmail.com
 * Date : 25.05.2022
 * Time : 14:47
 * Project name : uzAvtoSavdo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    @JsonProperty("user")
    private Users user;
    @JsonProperty("token_type")
    private String tokenType = "Bearer ";
    @JsonProperty("access_token")
    private String accessToken;

    public LoginResponse(Users user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }
}
