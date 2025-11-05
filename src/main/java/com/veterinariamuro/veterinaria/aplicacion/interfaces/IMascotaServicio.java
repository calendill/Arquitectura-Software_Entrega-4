package com.veterinariamuro.veterinaria.aplicacion.interfaces;

import java.util.List;

import com.veterinariamuro.veterinaria.aplicacion.Dto.mascotaDto.MascotaRequestDto;
import com.veterinariamuro.veterinaria.aplicacion.Dto.mascotaDto.MascotaResponseDto;

public interface IMascotaServicio {

    List<MascotaResponseDto> obtenerTodas();

    MascotaResponseDto obtenerPorId(Long id);

    MascotaResponseDto crearMascota(MascotaRequestDto requestDto);

    MascotaResponseDto actualizarMascota(Long id, MascotaRequestDto requestDto);

    String eliminarMascota(Long id);
}
