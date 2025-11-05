package com.veterinariamuro.veterinaria.aplicacion.servicios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veterinariamuro.veterinaria.aplicacion.Dto.veterinarioDto.VeterinarioRequestDto;
import com.veterinariamuro.veterinaria.aplicacion.Dto.veterinarioDto.VeterinarioResponseDto;
import com.veterinariamuro.veterinaria.aplicacion.interfaces.IVeterinarioServicio;
import com.veterinariamuro.veterinaria.dominio.model.Veterinario;
import com.veterinariamuro.veterinaria.infraestructura.dao.interfaces.IVeterinarioDao;

@Service
public class VeterinarioServicioImpl implements IVeterinarioServicio {

    @Autowired
    private IVeterinarioDao veterinarioDao; 
    // Inyección del DAO para acceder a los métodos que interactúan con la base de datos

    // -----------------------------
    // ✅ Obtener todos los veterinarios
    // -----------------------------
    @Override
    public List<VeterinarioResponseDto> obtenerTodos() {
        // Se obtiene la lista de veterinarios desde la base de datos y se convierte a DTOs
        return veterinarioDao.obtenerTodos()
                .stream()
                .map(this::convertirAResponseDto)
                .collect(Collectors.toList());
    }

    // -----------------------------
    // ✅ Obtener veterinario por ID
    // -----------------------------
    @Override
    public VeterinarioResponseDto obtenerPorId(Long id) {
        // Busca un veterinario por su ID, si existe lo convierte a DTO, si no devuelve null
        return veterinarioDao.obtenerPorId(id)
                .map(this::convertirAResponseDto)
                .orElse(null);
    }

    // -----------------------------
    // ✅ Crear veterinario
    // -----------------------------
    @Override
    public VeterinarioResponseDto crearVeterinario(VeterinarioRequestDto requestDto) {
        // Se convierte el DTO a entidad
        Veterinario veterinario = convertirAEntidad(requestDto);
        // Se guarda en la base de datos
        veterinarioDao.guardar(veterinario);
        // Se convierte de nuevo a DTO para devolverlo al cliente
        return convertirAResponseDto(veterinario);
    }

    // -----------------------------
    // ✅ Actualizar veterinario existente
    // -----------------------------
    @Override
    public VeterinarioResponseDto actualizarVeterinario(Long id, VeterinarioRequestDto requestDto) {
        Optional<Veterinario> optVeterinario = veterinarioDao.obtenerPorId(id);

        // Si no existe, se lanza un error
        if (optVeterinario.isEmpty()) {
            throw new RuntimeException("El veterinario con ID " + id + " no existe");
        }

        // Si existe, se actualizan los datos
        Veterinario veterinario = optVeterinario.get();
        veterinario.setNombre(requestDto.getNombre());
        veterinario.setApellido1(requestDto.getApellido1());
         veterinario.setApellido2(requestDto.getApellido2());
        veterinario.setTelefono(requestDto.getTelefono());
        veterinario.setCorreo(requestDto.getCorreo());
        veterinario.setDireccion(requestDto.getDireccion());
        // Se guarda la actualización en la base de datos
        veterinarioDao.actualizar(veterinario);

        // Se devuelve el veterinario actualizado en forma de DTO
        return convertirAResponseDto(veterinario);
    }

    // -----------------------------
    // ✅ Eliminar veterinario
    // -----------------------------
    @Override
    public String eliminarVeterinario(Long id) {
        Optional<Veterinario> veterinario = veterinarioDao.obtenerPorId(id);

        if (veterinario.isPresent()) {
            veterinarioDao.eliminar(veterinario.get());
            return "Veterinario con ID " + id + " eliminado correctamente";
        }
        return "Veterinario con ID " + id + " no encontrado";
    }

    // -----------------------------
    // 🔹 Métodos privados de conversión (Entidad ↔ DTO)
    // -----------------------------

    private VeterinarioResponseDto convertirAResponseDto(Veterinario veterinario) {
        // Convierte una entidad Veterinario a un DTO de respuesta
        return VeterinarioResponseDto.builder()
                .cedula(veterinario.getCedula())
                .nombre(veterinario.getNombre())
                .apellido1(veterinario.getApellido1())
                .apellido2(veterinario.getApellido2())
                .telefono(veterinario.getTelefono())
                .correo(veterinario.getCorreo())
                .direccion(veterinario.getDireccion())  
                .build();
    }

    private Veterinario convertirAEntidad(VeterinarioRequestDto dto) {
        // Convierte un DTO de solicitud a una entidad Veterinario
        return Veterinario.builder()
                .cedula(dto.getCedula())
                .nombre(dto.getNombre())
                .apellido1(dto.getApellido1())
                .apellido2(dto.getApellido2())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .direccion(dto.getDireccion())
                .build();
    }
}

