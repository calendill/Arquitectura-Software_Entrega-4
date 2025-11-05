package com.veterinariamuro.veterinaria.aplicacion.Dto.veterinarioDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class VeterinarioResponseDto {

    private Long cedula;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private int telefono;
    private String direccion;
    private String correo;

}
