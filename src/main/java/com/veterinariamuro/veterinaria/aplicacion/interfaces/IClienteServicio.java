package com.veterinariamuro.veterinaria.aplicacion.interfaces;

import java.util.List;

import com.veterinariamuro.veterinaria.aplicacion.Dto.clienteDto.ClienteRequestDto;
import com.veterinariamuro.veterinaria.aplicacion.Dto.clienteDto.ClienteResponseDto;

public interface IClienteServicio {

    List<ClienteResponseDto> obtenerTodos();

    ClienteResponseDto obtenerPorId(long cedula);

    ClienteResponseDto crearCliente(ClienteRequestDto clienteRequestDto);

    ClienteResponseDto actualizar(ClienteRequestDto clienteRequestDto, Long cedula);

    String eliminarCliente(long cedula);
}

