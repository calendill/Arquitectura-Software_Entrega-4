package com.veterinariamuro.veterinaria.dominio.factory;

import com.veterinariamuro.veterinaria.aplicacion.Dto.mascotaDto.MascotaRequestDto;
import com.veterinariamuro.veterinaria.aplicacion.Dto.mascotaDto.MascotaResponseDto;
import com.veterinariamuro.veterinaria.dominio.model.Cliente;
import com.veterinariamuro.veterinaria.dominio.model.Mascota;

public class MascotaFactory {

    // Método que crea una entidad Mascota (Perro o Gato) a partir de un DTO de request y un Cliente
    public static Mascota crearDesdeRequestDto(MascotaRequestDto dto, Cliente cliente) {
        // Determina el tipo de mascota y crea la instancia correspondiente usando switch expression
        return switch (dto.getTipoAnimal().toUpperCase()) {
            case "PERRO" -> new Perro(
                    dto.getNombre(),                     // Nombre de la mascota
                    dto.getFechaNacimiento(),            // Fecha de nacimiento
                    dto.getSexo(),                       // Sexo (M/F)
                    dto.getDescripcion(),                // Descripción opcional
                    dto.getProximaFechaVacunacion(),     // Próxima fecha de vacunación
                    cliente,                             // Cliente propietario
                    dto.getRaza()                        // Raza específica de perro
            );
            case "GATO" -> new Gato(
                    dto.getNombre(),                     // Nombre de la mascota
                    dto.getFechaNacimiento(),            // Fecha de nacimiento
                    dto.getSexo(),                       // Sexo (M/F)
                    dto.getDescripcion(),                // Descripción opcional
                    dto.getProximaFechaVacunacion(),     // Próxima fecha de vacunación
                    cliente,                             // Cliente propietario
                    dto.getColorPelaje()                 // Color de pelaje específico de gato
            );
            // Si el tipo de mascota no es reconocido, lanza excepción para evitar datos inválidos
            default -> throw new IllegalArgumentException("Tipo de mascota desconocido: " + dto.getTipoAnimal());
        };
    }

    // Método que convierte una entidad Mascota a un DTO de response listo para enviar al front-end
    public static MascotaResponseDto crearResponseDtoDesdeEntidad(Mascota mascota) {
        MascotaResponseDto dto = new MascotaResponseDto();

        // Asignación de atributos comunes de todas las mascotas
        dto.setId(mascota.getId());                         // ID de la mascota en la base de datos
        dto.setNombre(mascota.getNombre());                 // Nombre
        dto.setFechaNacimiento(mascota.getFechaNacimiento()); // Fecha de nacimiento
        dto.setSexo(mascota.getSexo());                     // Sexo
        dto.setDescripcion(mascota.getDescripcion());       // Descripción
        dto.setProximaFechaVacunacion(mascota.getProximaFechaVacunacion()); // Próxima vacuna

        // Guardamos solo el ID del cliente propietario para no exponer la entidad completa
        if (mascota.getCliente() != null) {
            dto.setClienteId(mascota.getCliente().getCedula());
        }

        // Asignación de atributos específicos según tipo de mascota
        switch (mascota) {
            case Perro perro -> {
                dto.setTipoAnimal("PERRO");  // Identifica el tipo
                dto.setRaza(perro.getRaza()); // Raza específica de perro
            }
            case Gato gato -> {
                dto.setTipoAnimal("GATO");      // Identifica el tipo
                dto.setColorPelaje(gato.getColorPelaje()); // Color específico de gato
            }
            default -> {
            }
        }

        // Retorna el DTO completo listo para enviarlo al cliente
        return dto;
    }
}
