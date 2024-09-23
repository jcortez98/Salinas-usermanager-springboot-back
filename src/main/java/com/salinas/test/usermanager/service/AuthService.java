package com.salinas.test.usermanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salinas.test.usermanager.dto.LoginUserDto;
import com.salinas.test.usermanager.dto.RegisterUserDto;
import com.salinas.test.usermanager.model.Role;
import com.salinas.test.usermanager.model.User;
import com.salinas.test.usermanager.repository.UserRepository;
import com.salinas.test.usermanager.service.interfaces.IAuthServices;
import com.salinas.test.usermanager.util.State;

@Service
public class AuthService implements IAuthServices{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User register(RegisterUserDto registerUserDto) {
        User user = new User();
        user.setName(registerUserDto.getName());
        user.setSurname(registerUserDto.getSurname());
        user.setState(State.ACTIVO);
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findRoleById(Long.valueOf(2)));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public User login(LoginUserDto loginUserDto) {
        
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );

        return userRepository.findUserByEmail(loginUserDto.getEmail())
                .orElseThrow();

    }

}
