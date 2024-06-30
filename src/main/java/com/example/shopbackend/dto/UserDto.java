package com.example.shopbackend.dto;

import com.example.shopbackend.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private UserRole userRole;
}
