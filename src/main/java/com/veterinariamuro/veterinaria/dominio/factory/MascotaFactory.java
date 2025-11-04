package com.veterinariamuro.veterinaria.dominio.factory;

import com.veterinariamuro.veterinaria.aplicacion.Dto.MascotaDto;
import com.veterinariamuro.veterinaria.dominio.model.Cliente;
import com.veterinariamuro.veterinaria.dominio.model.Mascota;

public class MascotaFactory {

    // Crea una entidad Mascota (Perro o Gato) a partir de un DTO y un Cliente
    public static Mascota crearDesdeDto(MascotaDto dto, Cliente cliente) {
        if ("PERRO".equalsIgnoreCase(dto.getTipoAnimal())) {
            // Si es perro, creamos un objeto Perro con todos sus atributos
            return new Perro(
                    dto.getNombre(),
                    dto.getFechaNacimiento(),
                    dto.getSexo(),
                    dto.getDescripcion(),
                    dto.getProximaFechaVacunacion(),
                    cliente,
                    dto.getRaza()
            );
        } else if ("GATO".equalsIgnoreCase(dto.getTipoAnimal())) {
            // Si es gato, creamos un objeto Gato con sus atributos específicos
            return new Gato(
                    dto.getNombre(),
                    dto.getFechaNacimiento(),
                    dto.getSexo(),
                    dto.getDescripcion(),
                    dto.getProximaFechaVacunacion(),
                    cliente,
                    dto.getColorPelaje()
            );
        } else {
            // Si el tipo no es reconocido, lanzamos una excepción
            throw new IllegalArgumentException("Tipo de mascota desconocido: " + dto.getTipoAnimal());
        }
    }

    // Convierte una entidad Mascota a un DTO para enviar al front-end
    public static MascotaDto crearDtoDesdeEntidad(Mascota mascota) {
        MascotaDto dto = new MascotaDto();
        dto.setId(mascota.getId());
        dto.setNombre(mascota.getNombre());
        dto.setFechaNacimiento(mascota.getFechaNacimiento());
        dto.setSexo(mascota.getSexo());
        dto.setDescripcion(mascota.getDescripcion());
        dto.setProximaFechaVacunacion(mascota.getProximaFechaVacunacion());

        // Guardamos solo el ID del cliente para no enviar toda la entidad
        if (mascota.getCliente() != null) {
            dto.setClienteId(mascota.getCliente().getCedula());
        }

        // Detectamos el tipo de mascota y asignamos los atributos específicos
        switch (mascota) {
            case Perro perro -> {
                dto.setTipoAnimal("PERRO");
                dto.setRaza(perro.getRaza());
            }
            case Gato gato -> {
                dto.setTipoAnimal("GATO");
                dto.setColorPelaje(gato.getColorPelaje());
            }
            default -> {
                // No hacemos nada si es otro tipo
            }
        }

        return dto; // Retornamos el DTO listo para enviar al front-end
    }
}
