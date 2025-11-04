package com.veterinariamuro.veterinaria.aplicacion.servicios;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.veterinariamuro.veterinaria.aplicacion.Dto.ClienteDto;
import com.veterinariamuro.veterinaria.aplicacion.interfaces.IClienteServicio;
import com.veterinariamuro.veterinaria.dominio.eventos.ClienteEventos;
import com.veterinariamuro.veterinaria.dominio.model.Cliente;
import com.veterinariamuro.veterinaria.infraestructura.dao.interfaces.IClienteDao;

@Service
public class ClienteServicioImpl implements IClienteServicio {

    @Autowired
    private ApplicationEventPublisher eventPublisher; 
    // Para publicar eventos cuando se crea un cliente

    @Autowired
    private IClienteDao clienteDao; 
    // DAO para acceder a la base de datos

    @Override
    public List<ClienteDto> obtenerTodos() {
        // Obtiene todos los clientes y los convierte a DTO
        return clienteDao.obtenerTodos()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDto obtenerPorId(long cedula) {
        // Busca un cliente por su cédula y lo convierte a DTO, o retorna null si no existe
        return clienteDao.obtenerPorId(cedula)
                .map(this::convertirADto)
                .orElse(null);
    }

    @Override
    public ClienteDto CrearCliente(ClienteDto clienteDto) {
        // 1️⃣ Convertir DTO a entidad
        Cliente cliente = convertirAEntidad(clienteDto);

        // 2️⃣ Guardar en la base de datos
        clienteDao.GuardarCliente(cliente);

        // 3️⃣ Crear DTO actualizado
        ClienteDto clienteGuardadoDto = convertirADto(cliente);

        // 4️⃣ Publicar evento de cliente creado
        ClienteEventos evento = new ClienteEventos(this, clienteGuardadoDto);
        eventPublisher.publishEvent(evento);

        // 5️⃣ Devolver DTO guardado
        return clienteGuardadoDto;
    }

    @Override
    public ClienteDto actualizar(ClienteDto clienteDto, Long cedula) {
        // Buscar cliente por cédula
        Optional<Cliente> optCliente = clienteDao.obtenerPorId(cedula);

        if (optCliente.isEmpty()) {
            throw new RuntimeException("El cliente no existe"); 
            // Lanza excepción si no se encuentra
        }

        // Actualizar campos del cliente
        Cliente cliente = optCliente.get();
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellido1(clienteDto.getApellido1());
        cliente.setApellido2(clienteDto.getApellido2());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setCorreo(clienteDto.getCorreo());
        cliente.setDireccion(clienteDto.getDireccion());

        clienteDao.actualizar(cliente); // Guardar cambios en la base de datos
        return convertirADto(cliente); // Devolver DTO actualizado
    }

    @Override
    public String eliminarCliente(long cedula) {
        // Buscar cliente por cédula
        Optional<Cliente> cliente = clienteDao.obtenerPorId(cedula);
        if (cliente.isPresent()) {
            clienteDao.eliminarCliente(cliente.get()); // Eliminar si existe
            return "Cliente con cédula " + cedula + " eliminado";
        }
        return "Cliente con cédula " + cedula + " no encontrado"; // Retornar mensaje si no existe
    }

    // ✅ Métodos privados de conversión

    private ClienteDto convertirADto(Cliente cliente) {
        // Convierte entidad Cliente a DTO usando builder
        return ClienteDto.builder()
                .cedula(cliente.getCedula())
                .nombre(cliente.getNombre())
                .apellido1(cliente.getApellido1())
                .apellido2(cliente.getApellido2())
                .telefono(cliente.getTelefono())
                .correo(cliente.getCorreo())
                .direccion(cliente.getDireccion())
                .build();
    }

    private Cliente convertirAEntidad(ClienteDto dto) {
        // Convierte DTO a entidad Cliente usando builder
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
