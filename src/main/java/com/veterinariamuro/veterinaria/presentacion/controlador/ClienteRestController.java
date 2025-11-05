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

import com.veterinariamuro.veterinaria.aplicacion.Dto.clienteDto.ClienteRequestDto;
import com.veterinariamuro.veterinaria.aplicacion.Dto.clienteDto.ClienteResponseDto;
import com.veterinariamuro.veterinaria.aplicacion.interfaces.IClienteServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con clientes de la veterinaria")
public class ClienteRestController {

    @Autowired
    private IClienteServicio clienteServicio;

    // -----------------------
    // ✅ Listar todos los clientes
    // -----------------------
    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Obtiene una lista completa de clientes registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<ClienteResponseDto>> listarClientes() {
        List<ClienteResponseDto> clientes = clienteServicio.obtenerTodos();
        return ResponseEntity.ok(clientes);
    }

    // -----------------------
    // ✅ Obtener un cliente por cédula
    // -----------------------
    @GetMapping("/{cedula}")
    @Operation(summary = "Obtener un cliente por cédula", description = "Recupera la información de un cliente mediante su cédula")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "message": "Cliente con cédula 999 no encontrado",
                      "path": "/api/clientes/999",
                      "timestamp": "2025-11-04T16:10:00"
                    }
                """)))
    })
    public ResponseEntity<ClienteResponseDto> obtenerCliente(
            @Parameter(description = "Cédula del cliente", required = true)
            @PathVariable Long cedula) {

        ClienteResponseDto cliente = clienteServicio.obtenerPorId(cedula);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            throw new RuntimeException("Cliente con cédula " + cedula + " no encontrado");
        }
    }

    // -----------------------
    // ✅ Crear un nuevo cliente
    // -----------------------
    @PostMapping
    @Operation(summary = "Crear un nuevo cliente", description = "Registra un cliente nuevo en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado correctamente",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "cedula": 123456789,
                      "nombre": "Carlos",
                      "apellido1": "Pérez",
                      "apellido2": "López",
                      "telefono": 3101234567,
                      "direccion": "Calle 45 #12-34",
                      "correo": "carlos.perez@example.com"
                    }
                """))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "status": 400,
                      "message": "El campo 'nombre' no puede estar vacío",
                      "path": "/api/clientes",
                      "timestamp": "2025-11-04T16:05:00"
                    }
                """)))
    })
    public ResponseEntity<ClienteResponseDto> crearCliente(
            @Valid @RequestBody ClienteRequestDto clienteRequestDto) {

        ClienteResponseDto creado = clienteServicio.crearCliente(clienteRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // -----------------------
    // ✅ Actualizar cliente existente
    // -----------------------
    @PutMapping("/{cedula}")
    @Operation(summary = "Actualizar un cliente existente", description = "Actualiza la información de un cliente mediante su cédula")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ClienteResponseDto> actualizarCliente(
            @Valid @RequestBody ClienteRequestDto clienteRequestDto,
            @Parameter(description = "Cédula del cliente", required = true)
            @PathVariable Long cedula) {

        ClienteResponseDto actualizado = clienteServicio.actualizar(clienteRequestDto, cedula);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            throw new RuntimeException("Cliente con cédula " + cedula + " no encontrado");
        }
    }

    // -----------------------
    // ✅ Eliminar cliente
    // -----------------------
    @DeleteMapping("/{cedula}")
    @Operation(summary = "Eliminar un cliente", description = "Elimina un cliente registrado mediante su cédula")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "message": "Cliente con cédula 123456 no encontrado",
                      "path": "/api/clientes/123456",
                      "timestamp": "2025-11-04T16:12:00"
                    }
                """)))
    })
    public ResponseEntity<Void> eliminarCliente(
            @Parameter(description = "Cédula del cliente", required = true)
            @PathVariable Long cedula) {

        String resultado = clienteServicio.eliminarCliente(cedula);
        if (resultado.contains("eliminado")) {
            return ResponseEntity.noContent().build();
        } else {
            throw new RuntimeException("Cliente con cédula " + cedula + " no encontrado");
        }
    }
}
