package com.veterinariamuro.veterinaria.presentacion.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veterinariamuro.veterinaria.aplicacion.Dto.ClienteDto;
import com.veterinariamuro.veterinaria.aplicacion.interfaces.IClienteServicio;

@RestController
@RequestMapping("/api/clientes") // Prefijo para todas las rutas de este controlador
public class ClienteRestController {

    @Autowired
    private IClienteServicio clienteServicio; 
    // Inyectamos el servicio que maneja la lógica de negocio de clientes

    // ✅ Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<ClienteDto>> listarClientes() {
        // Llamamos al servicio para obtener todos los clientes
        List<ClienteDto> clientes = clienteServicio.obtenerTodos();
        return ResponseEntity.ok(clientes); 
        // Retornamos 200 OK con la lista de clientes
    }

    // ✅ Obtener un cliente por cédula
    @GetMapping("/{cedula}")
    public ResponseEntity<ClienteDto> obtenerCliente(@PathVariable Long cedula) {
        // PathVariable toma el valor de la URL
        ClienteDto cliente = clienteServicio.obtenerPorId(cedula);
        if (cliente != null) {
            return ResponseEntity.ok(cliente); // Si existe, 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Si no existe, 404
        }
    }

    // ✅ Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<ClienteDto> crearCliente(@RequestBody ClienteDto clienteDto) {
        // RequestBody indica que recibimos datos en formato JSON
        ClienteDto creado = clienteServicio.CrearCliente(clienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); 
        // Retornamos 201 CREATED con el cliente creado
    }

    // ✅ Actualizar cliente
    @PutMapping("/{cedula}")
    public ResponseEntity<ClienteDto> actualizarCliente(@RequestBody ClienteDto clienteDto,
                                                        @PathVariable Long cedula) {
        // Llamamos al servicio para actualizar el cliente por su cédula
        ClienteDto actualizado = clienteServicio.actualizar(clienteDto, cedula);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado); // Si se actualizó, 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Si no existe, 404
        }
    }

    // ✅ Eliminar cliente
    @DeleteMapping("/{cedula}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long cedula) {
        // Llamamos al servicio para eliminar
        String eliminado = clienteServicio.eliminarCliente(cedula);
        if (eliminado != null) {
            return ResponseEntity.noContent().build(); 
            // 204 No Content indica que se eliminó correctamente
        } else {
            return ResponseEntity.notFound().build(); // 404 si no existe
        }
    }
}
