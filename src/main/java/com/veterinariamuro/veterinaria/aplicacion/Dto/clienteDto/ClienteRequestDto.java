package com.veterinariamuro.veterinaria.aplicacion.Dto.clienteDto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDto implements Serializable {

    private Long cedula;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private Long telefono;
    private String direccion;
    private String correo;
}
