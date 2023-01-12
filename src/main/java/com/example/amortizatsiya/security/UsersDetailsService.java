package com.example.amortizatsiya.security;

import com.example.amortizatsiya.security.entity.Users;
import com.example.amortizatsiya.security.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UsersDetailsService implements UserDetailsService {

    private final UserService userService;

    public UsersDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userService.findByUsername(username);
        return UserPrincipal.create(user);
    }
}
