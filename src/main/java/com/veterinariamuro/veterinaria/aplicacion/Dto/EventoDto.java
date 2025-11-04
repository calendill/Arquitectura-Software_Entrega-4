package com.veterinariamuro.veterinaria.aplicacion.Dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EventoDto {

    private Long totalClientes; 
    // Guarda el total de clientes registrados en el sistema

    private LocalDateTime fechaCreacion; 
    // Guarda la fecha y hora del último cliente creado

    public EventoDto(long totalClientes, LocalDateTime fechaCreacion) {
        this.totalClientes = totalClientes; 
        this.fechaCreacion = fechaCreacion; 
        // Constructor para inicializar los atributos
    }
}
