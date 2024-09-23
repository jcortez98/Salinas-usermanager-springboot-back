package com.salinas.test.usermanager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salinas.test.usermanager.dto.UpdatePasswordDto;
import com.salinas.test.usermanager.dto.UpdateUserDto;
import com.salinas.test.usermanager.model.User;
import com.salinas.test.usermanager.service.UserService;

@RequestMapping(value = "/api/v1/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    
    @GetMapping(value = "/")
    public ResponseEntity<?> getAllUsers(){
        try {
            Map<String, List<User>> resp = new HashMap<>();
            resp.put("payload", userService.getAllUsers());
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            Map<String, String> resp = new HashMap<>();
            resp.put("message", "Hubo un error al generar la lista de usuarios");
            resp.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(resp);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id){
        try {
            Map<String, User> resp = new HashMap<>();
            resp.put("payload", userService.findUserById(id));
            return ResponseEntity.ok(resp);
        } catch (NoSuchElementException e) {
            Map<String, String> resp = new HashMap<>();
            resp.put("message", "Usuario no encontrado");
            resp.put("error", e.getMessage());
            return ResponseEntity.status(404).body(resp);
        } catch (Exception e) {
            Map<String, String> resp = new HashMap<>();
            resp.put("message", "Hubo un error al buscar el usuario");
            resp.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(resp);
        }
    }

    @PutMapping(value = "/{id}")
    public <T> ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto){
        Map<String, T> resp = new HashMap<>();
        try {
            User user = userService.findUserById(id);
            if (!userService.alredyExistUser(updateUserDto.getEmail(), user.getId())) {
                user.setName(updateUserDto.getName());
                user.setSurname(updateUserDto.getSurname());
                user.setState(updateUserDto.getState());
                user.setEmail(updateUserDto.getEmail());
                userService.saveUser(user);
                resp.put("message", (T)"Usuario modificado con éxito");
                resp.put("payload", (T)user);
                return ResponseEntity.ok(resp);
            }else{
                resp.put("message", (T)"Hubo un error en el registro");
                resp.put("error", (T)"El email ya se encuentra registrado");
                return ResponseEntity.status(409).body(resp);
            }
        } catch (Exception e) {
            resp.put("message", (T)"Hubo un error al modificar el usuario");
            resp.put("error", (T)e.getMessage());
            return ResponseEntity.internalServerError().body(resp);
        }
    }

    @PutMapping(value = "/update-password/{id}")
    public ResponseEntity<Map<String,String>> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordDto updatePasswordDto){
        Map<String,String> resp = new HashMap<>();
        try {
            User user = userService.findUserById(id);
            if(userService.checkChangePassword(user, updatePasswordDto.getOldPassword())){
                user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
                userService.saveUser(user);

                resp.put("message", "Cambio de contraseña exitoso");
                return ResponseEntity.ok(resp);
            }else{
                resp.put("message","Hubo un error al cambiar la contraseña");
                resp.put("error", "La contraseña antigua no coincide");

                return ResponseEntity.status(401).body(resp);
            }
        } catch (Exception e) {
            resp.put("message", "Hubo un error al cambiar la contraseña");
            resp.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(resp);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        Map<String,String> resp = new HashMap<>();
        try {
            User user = userService.findUserById(id);
            userService.deleteUser(user);
            resp.put("message", "Usuario eliminado con éxito");
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            resp.put("message", "Hubo un error al eliminar el registro");
            resp.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(resp);
        }
    }

    
}
