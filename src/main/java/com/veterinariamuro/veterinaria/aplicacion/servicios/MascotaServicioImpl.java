package com.veterinariamuro.veterinaria.aplicacion.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.veterinariamuro.veterinaria.aplicacion.Dto.mascotaDto.MascotaRequestDto;
import com.veterinariamuro.veterinaria.aplicacion.Dto.mascotaDto.MascotaResponseDto;
import com.veterinariamuro.veterinaria.aplicacion.interfaces.IMascotaServicio;
import com.veterinariamuro.veterinaria.dominio.factory.Gato;
import com.veterinariamuro.veterinaria.dominio.factory.MascotaFactory;
import com.veterinariamuro.veterinaria.dominio.factory.Perro;
import com.veterinariamuro.veterinaria.dominio.model.Cliente;
import com.veterinariamuro.veterinaria.dominio.model.Mascota;
import com.veterinariamuro.veterinaria.infraestructura.dao.interfaces.IClienteDao;
import com.veterinariamuro.veterinaria.infraestructura.dao.interfaces.IMascotaDao;

@Service
public class MascotaServicioImpl implements IMascotaServicio {

    @Autowired
    private IMascotaDao mascotaDao;

    @Autowired
    private IClienteDao clienteDao;

  
    @Override
    public List<MascotaResponseDto> obtenerTodas() {
        return mascotaDao.obtenerTodas()
                .stream()
                .map(MascotaFactory::crearResponseDtoDesdeEntidad)
                .collect(Collectors.toList());
    }


    @Override
    public MascotaResponseDto obtenerPorId(Long id) {
        Mascota mascota = mascotaDao.obtenerPorId(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada")
                );
        return MascotaFactory.crearResponseDtoDesdeEntidad(mascota);
    }

    @Override
    public MascotaResponseDto crearMascota(MascotaRequestDto requestDto) {
        Cliente cliente = clienteDao.obtenerPorId(requestDto.getClienteId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado")
                );

        Mascota mascota = MascotaFactory.crearDesdeRequestDto(requestDto, cliente);
        mascotaDao.guardar(mascota);

        return MascotaFactory.crearResponseDtoDesdeEntidad(mascota);
    }


    @Override
    public MascotaResponseDto actualizarMascota(Long id, MascotaRequestDto requestDto) {
        Mascota mascota = mascotaDao.obtenerPorId(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada")
                );

        // Actualiza atributos generales
        mascota.setNombre(requestDto.getNombre());
        mascota.setDescripcion(requestDto.getDescripcion());
        mascota.setSexo(requestDto.getSexo());
        mascota.setFechaNacimiento(requestDto.getFechaNacimiento());

        // Atributos específicos según tipo
        switch (mascota) {
            case Perro perro -> perro.setRaza(requestDto.getRaza());
            case Gato gato -> gato.setColorPelaje(requestDto.getColorPelaje());
            default -> {
            }
        }

        mascotaDao.actualizar(mascota);
        return MascotaFactory.crearResponseDtoDesdeEntidad(mascota);
    }


    @Override
    public String eliminarMascota(Long id) {
        Mascota mascota = mascotaDao.obtenerPorId(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Mascota no encontrada")
                );

        mascotaDao.eliminar(mascota);
        return "Mascota eliminada correctamente";
    }
}
