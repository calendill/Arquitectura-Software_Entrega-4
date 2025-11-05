package com.veterinariamuro.veterinaria.aplicacion.interfaces;

import java.util.List;

import com.veterinariamuro.veterinaria.aplicacion.Dto.veterinarioDto.VeterinarioRequestDto;
import com.veterinariamuro.veterinaria.aplicacion.Dto.veterinarioDto.VeterinarioResponseDto;


public interface IVeterinarioServicio {

    List<VeterinarioResponseDto> obtenerTodos();

    VeterinarioResponseDto obtenerPorId(Long id);

    VeterinarioResponseDto crearVeterinario(VeterinarioRequestDto requestDto);

    VeterinarioResponseDto actualizarVeterinario(Long id, VeterinarioRequestDto requestDto);

    String eliminarVeterinario(Long id);
    
}
