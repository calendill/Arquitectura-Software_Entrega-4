package com.veterinariamuro.veterinaria.aplicacion.servicios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veterinariamuro.veterinaria.aplicacion.Dto.MascotaDto;
import com.veterinariamuro.veterinaria.aplicacion.interfaces.IMascotaServicio;
import com.veterinariamuro.veterinaria.dominio.factory.MascotaFactory;
import com.veterinariamuro.veterinaria.dominio.model.Cliente;
import com.veterinariamuro.veterinaria.dominio.model.Mascota;
import com.veterinariamuro.veterinaria.infraestructura.dao.interfaces.IClienteDao;
import com.veterinariamuro.veterinaria.infraestructura.dao.interfaces.IMascotaDao;

@Service
public class MascotaServicioImpl implements IMascotaServicio {

    @Autowired
    private IMascotaDao mascotaDao; 
    // DAO para acceder a la base de datos de mascotas

    @Autowired
    private IClienteDao clienteDao; 
    // DAO para acceder a los clientes (necesario para asociar mascotas a clientes)

    @Override
    public List<MascotaDto> obtenerTodas() {
        // Obtiene todas las mascotas y las convierte a DTO usando el Factory
        return mascotaDao.obtenerTodas()
                .stream()
                .map(MascotaFactory::crearDtoDesdeEntidad)
                .collect(Collectors.toList());
    }

    @Override
    public MascotaDto obtenerPorId(Long id) {
        // Busca una mascota por ID y la convierte a DTO, o devuelve null si no existe
        return mascotaDao.obtenerPorId(id)
                .map(MascotaFactory::crearDtoDesdeEntidad)
                .orElse(null);
    }

    @Override
    public MascotaDto crearMascota(MascotaDto mascotaDto) {
        // Busca el cliente al que pertenece la mascota
        Cliente cliente = clienteDao.obtenerPorId(mascotaDto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Crea la entidad Mascota (Perro o Gato) usando el Factory
        Mascota mascota = MascotaFactory.crearDesdeDto(mascotaDto, cliente);
        mascotaDao.guardar(mascota); // Guarda en la base de datos

        // Devuelve el DTO actualizado
        return MascotaFactory.crearDtoDesdeEntidad(mascota);
    }

    @Override
    public MascotaDto actualizarMascota(Long id, MascotaDto dto) {
        Optional<Mascota> optMascota = mascotaDao.obtenerPorId(id);
        if (optMascota.isEmpty()) {
            throw new RuntimeException("Mascota no encontrada"); // Manejo de error
        }

        // Actualiza los atributos generales de la mascota
        Mascota mascota = optMascota.get();
        mascota.setNombre(dto.getNombre());
        mascota.setDescripcion(dto.getDescripcion());
        mascota.setSexo(dto.getSexo());
        mascota.setFechaNacimiento(dto.getFechaNacimiento());

        // Los atributos específicos (raza, colorPelaje) se manejan en el Factory si es necesario
        mascotaDao.actualizar(mascota);
        return MascotaFactory.crearDtoDesdeEntidad(mascota); // Devuelve DTO actualizado
    }

    @Override
    public String eliminarMascota(Long id) {
        // Busca mascota por ID
        Optional<Mascota> mascota = mascotaDao.obtenerPorId(id);
        if (mascota.isPresent()) {
            mascotaDao.eliminar(mascota.get()); // Elimina si existe
            return "Mascota eliminada correctamente";
        }
        return "Mascota no encontrada"; // Retorna mensaje si no existe
    }
}

