package com.example.shopbackend.service.auth;

import com.example.shopbackend.dto.SignupRequest;
import com.example.shopbackend.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);
    Boolean hasUserWithEmail(String email);
}
