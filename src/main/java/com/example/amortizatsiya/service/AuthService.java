package com.example.amortizatsiya.service;

import com.example.amortizatsiya.dto.UserDto;
import com.example.amortizatsiya.dto.auth.LoginRequest;
import com.example.amortizatsiya.dto.auth.LoginResponse;
import com.example.amortizatsiya.dto.auth.RegisterDto;
import com.example.amortizatsiya.exception.BadRequestException;
import com.example.amortizatsiya.exception.CustomNotFoundException;
import com.example.amortizatsiya.exception.handler.ApiErrorMessages;
import com.example.amortizatsiya.security.entity.Roles;
import com.example.amortizatsiya.security.entity.Users;
import com.example.amortizatsiya.security.entity.enums.RoleName;
import com.example.amortizatsiya.security.jwt.JwtUtils;
import com.example.amortizatsiya.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@RequiredArgsConstructor
@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = getAuthentication(request.getUsername(), request.getPassword());
        if (authentication == null)
            throw new CustomNotFoundException(String.format(ApiErrorMessages.NOT_FOUND + "User not found"));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Users user = userService.findByUsername(request.getUsername());
        log.info("User with [username: {}] has logged in", user.getUsername());

        return new LoginResponse(user, jwtUtils.generateToken(authentication));
    }

//    public UserDto register(RegisterDto dto) {
//        if (Boolean.TRUE.equals(userService.existsUser(dto.getUsername())))
//            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "User already exists!"));
//
////        Users users = new Users();
////        users.setUsername(dto.getUsername());
////        users.setRoles(Set.of(Roles.builder().name(RoleName.USER).build()));
////        users.setPassword(passwordEncoder.encode(dto.getPassword()));
////        users.setIsEnabled(Boolean.TRUE);
////        return userService.saveUser(users)
//
//        return userService.saveUser(
//                Users.builder()
//                        .username(dto.getUsername())
//                        .password(passwordEncoder.encode(dto.getPassword()))
//                        .isEnabled(true)
//                        .build()
//        );
//    }

    private Authentication getAuthentication(String username, String password) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
        } catch (RuntimeException exception) {
            throw new BadRequestException(String.format(ApiErrorMessages.BAD_REQUEST + "%s", exception.getMessage()));
        }
        return authentication;
    }
}
