package com.veterinariamuro.veterinaria.aplicacion.servicios;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.veterinariamuro.veterinaria.aplicacion.Dto.clienteDto.ClienteRequestDto;
import com.veterinariamuro.veterinaria.aplicacion.Dto.clienteDto.ClienteResponseDto;
import com.veterinariamuro.veterinaria.aplicacion.interfaces.IClienteServicio;
import com.veterinariamuro.veterinaria.dominio.eventos.ClienteEventos;
import com.veterinariamuro.veterinaria.dominio.model.Cliente;
import com.veterinariamuro.veterinaria.infraestructura.dao.interfaces.IClienteDao;

@Service
public class ClienteServicioImpl implements IClienteServicio {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private IClienteDao clienteDao;

    @Override
    public List<ClienteResponseDto> obtenerTodos() {
        return clienteDao.obtenerTodos()
                .stream()
                .map(this::convertirAResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponseDto obtenerPorId(long cedula) {
        return clienteDao.obtenerPorId(cedula)
                .map(this::convertirAResponseDto)
                .orElse(null);
    }

    @Override
    public ClienteResponseDto crearCliente(ClienteRequestDto requestDto) {
        Cliente cliente = convertirAEntidad(requestDto);
        clienteDao.GuardarCliente(cliente);

        ClienteResponseDto responseDto = convertirAResponseDto(cliente);

        // Publicar evento
        ClienteEventos evento = new ClienteEventos(this, responseDto);
        eventPublisher.publishEvent(evento);

        return responseDto;
    }

    @Override
    public ClienteResponseDto actualizar(ClienteRequestDto requestDto, Long cedula) {
        Optional<Cliente> optCliente = clienteDao.obtenerPorId(cedula);

        if (optCliente.isEmpty()) {
            throw new RuntimeException("El cliente no existe");
        }

        Cliente cliente = optCliente.get();
        cliente.setNombre(requestDto.getNombre());
        cliente.setApellido1(requestDto.getApellido1());
        cliente.setApellido2(requestDto.getApellido2());
        cliente.setTelefono(requestDto.getTelefono());
        cliente.setCorreo(requestDto.getCorreo());
        cliente.setDireccion(requestDto.getDireccion());

        clienteDao.actualizar(cliente);

        return convertirAResponseDto(cliente);
    }

    @Override
    public String eliminarCliente(long cedula) {
        Optional<Cliente> cliente = clienteDao.obtenerPorId(cedula);
        if (cliente.isPresent()) {
            clienteDao.eliminarCliente(cliente.get());
            return "Cliente con cédula " + cedula + " eliminado";
        }
        return "Cliente con cédula " + cedula + " no encontrado";
    }

    // 🔹 Métodos privados de conversión

    private ClienteResponseDto convertirAResponseDto(Cliente cliente) {
        return ClienteResponseDto.builder()
                .cedula(cliente.getCedula())
                .nombre(cliente.getNombre())
                .apellido1(cliente.getApellido1())
                .apellido2(cliente.getApellido2())
                .telefono(cliente.getTelefono())
                .correo(cliente.getCorreo())
                .direccion(cliente.getDireccion())
                .build();
    }

    private Cliente convertirAEntidad(ClienteRequestDto dto) {
        return Cliente.builder()
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
