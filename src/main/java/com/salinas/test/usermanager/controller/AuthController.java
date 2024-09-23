package com.salinas.test.usermanager.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salinas.test.usermanager.dto.AuthResponseDto;
import com.salinas.test.usermanager.dto.LoginUserDto;
import com.salinas.test.usermanager.dto.RegisterUserDto;
import com.salinas.test.usermanager.model.User;
import com.salinas.test.usermanager.security.JwtConfig;
import com.salinas.test.usermanager.service.AuthService;
import com.salinas.test.usermanager.service.UserService;

import jakarta.validation.Valid;

@RequestMapping(value = "/auth")
@RestController
public class AuthController{

    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public <T> ResponseEntity<Map<String,T>> register(@Valid @RequestBody RegisterUserDto registerUserDto){

        Map<String, T> resp = new HashMap<>();
        
        try {
            if (!userService.alredyExistUser(registerUserDto.getEmail())) {
                User registerdUser = authService.register(registerUserDto);
                resp.put("message", (T)"Usuario registrado con éxito");
                resp.put("payload", (T)registerdUser);
                return ResponseEntity.status(201).body(resp);
            }else{
                resp.put("message", (T)"Hubo un error en el registro");
                resp.put("error", (T)"El email ya se encuentra registrado");
                return ResponseEntity.status(409).body(resp);
            }
            
        } catch (Exception e) {
            resp.put("message", (T)"Hubo un error al validar sus datos");
            resp.put("error", (T)e.getMessage());
            return ResponseEntity.internalServerError().body(resp);
        }
    }

    @PostMapping(value = "/login")
    public <T> ResponseEntity<Map<String, T>> login(@Valid @RequestBody LoginUserDto loginUserDto){
        
        Map<String, T> resp = new HashMap<>();
            
        try {
            User authenticatedUser = authService.login(loginUserDto);
            String jwtToken = jwtConfig.buildToken(authenticatedUser.getRoles() ,authenticatedUser);
            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setToken(jwtToken);
            authResponseDto.setExpiresIn(jwtConfig.getJwtExpiration());
            User user = userService.findUserByEmail(loginUserDto.getEmail());
            authResponseDto.setUser(user);

            resp.put("message", (T)"Logueado con éxito");
            resp.put("payload", (T)authResponseDto);

            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            resp.put("message", (T) "Error al iniciar sesión");
            resp.put("error", (T) e.getMessage());

            return ResponseEntity.status(401).body(resp);
        }
    }

}
