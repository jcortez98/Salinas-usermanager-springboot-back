package com.salinas.test.usermanager.dto;

import java.util.List;

import com.salinas.test.usermanager.model.Role;
import com.salinas.test.usermanager.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponseDto {

    private String token;
    private Long expiresIn;
    private User user;
}
