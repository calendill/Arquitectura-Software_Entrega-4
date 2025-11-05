package com.veterinariamuro.veterinaria.aplicacion.Dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // evita problemas con Springdoc
public class ErrorResponse {

    private int status;
    private String message;
    private String path;

    @Schema(type = "string", example = "2025-11-04T14:45:00")
    private LocalDateTime timestamp;
}
