package com.salinas.test.usermanager.dto;

import com.salinas.test.usermanager.util.State;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class UpdateUserDto {

    @NotBlank(message = "Debe proporcionar al menos un nombre")
    private String name;
    @NotBlank(message = "Debe proporcionar al menos un apellido")
    private String surname;
    @NotNull(message = "Debe seleccionar un estado para el usuario")
    private State state;
    @Column(unique = true)
    @Email(message = "Proporcione un correo válido")
    @NotBlank(message = "Debe proporcionar un correo electrónico")
    private String email;

}
