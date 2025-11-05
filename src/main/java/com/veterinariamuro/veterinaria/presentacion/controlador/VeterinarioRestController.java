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

import com.veterinariamuro.veterinaria.aplicacion.Dto.ErrorResponse;
import com.veterinariamuro.veterinaria.aplicacion.Dto.veterinarioDto.VeterinarioRequestDto;
import com.veterinariamuro.veterinaria.aplicacion.Dto.veterinarioDto.VeterinarioResponseDto;
import com.veterinariamuro.veterinaria.aplicacion.interfaces.IVeterinarioServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

// Este controlador REST maneja todas las peticiones relacionadas con los veterinarios.
// Es la capa más externa del backend (interactúa directamente con el cliente o front-end).
@RestController
@RequestMapping("/api/veterinarios")
@Tag(name = "Veterinarios", description = "Operaciones relacionadas con los veterinarios del sistema")
public class VeterinarioRestController {

    // Se inyecta el servicio de veterinarios para acceder a la lógica de negocio.
    @Autowired
    private IVeterinarioServicio veterinarioServicio;

    // -----------------------------
    // ✅ Obtener todos los veterinarios
    // -----------------------------
    @GetMapping
    @Operation(summary = "Listar todos los veterinarios", description = "Devuelve una lista con todos los veterinarios registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<VeterinarioResponseDto>> listarVeterinarios() {
        // Llamamos al servicio para traer todos los veterinarios.
        List<VeterinarioResponseDto> veterinarios = veterinarioServicio.obtenerTodos();
        return ResponseEntity.ok(veterinarios);
    }

    // -----------------------------
    // ✅ Obtener veterinario por ID
    // -----------------------------
    @GetMapping("/{id}")
    @Operation(summary = "Obtener veterinario por ID", description = "Permite buscar un veterinario usando su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Veterinario encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "id": 1,
                      "nombre": "Dr. Juan Pérez",
                      "especialidad": "Cirugía animal",
                      "telefono": "3216549870",
                      "correo": "juanperez@veterinaria.com",
                      "direccion": "Calle 45 #10-20"
                    }
                """))),
        @ApiResponse(responseCode = "404", description = "Veterinario no encontrado",
            content = @Content(mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "message": "Veterinario con ID 10 no encontrado",
                      "path": "/api/veterinarios/10",
                      "timestamp": "2025-11-04T21:00:00"
                    }
                """)))
    })
    public ResponseEntity<VeterinarioResponseDto> obtenerVeterinario(
            @Parameter(description = "ID del veterinario", required = true)
            @PathVariable Long id) {

        // Se busca el veterinario con el servicio
        VeterinarioResponseDto veterinario = veterinarioServicio.obtenerPorId(id);
        if (veterinario != null) {
            return ResponseEntity.ok(veterinario);
        } else {
            throw new RuntimeException("Veterinario con ID " + id + " no encontrado");
        }
    }

    // -----------------------------
    // ✅ Crear nuevo veterinario
    // -----------------------------
    @PostMapping
    @Operation(summary = "Registrar nuevo veterinario", description = "Permite crear un nuevo veterinario en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Veterinario creado correctamente",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "id": 2,
                      "nombre": "Dra. María López",
                      "especialidad": "Dermatología veterinaria",
                      "telefono": "3107896541",
                      "correo": "marialopez@veterinaria.com",
                      "direccion": "Carrera 12 #56-90"
                    }
                """))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(value = """
                    {
                      "status": 400,
                      "message": "El campo 'nombre' es obligatorio",
                      "path": "/api/veterinarios",
                      "timestamp": "2025-11-04T21:05:00"
                    }
                """)))
    })
    public ResponseEntity<VeterinarioResponseDto> crearVeterinario(
            @RequestBody VeterinarioRequestDto requestDto) {

        // Se envía el DTO al servicio para guardar el nuevo veterinario.
        VeterinarioResponseDto creado = veterinarioServicio.crearVeterinario(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // -----------------------------
    // ✅ Actualizar veterinario existente
    // -----------------------------
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar veterinario", description = "Modifica los datos de un veterinario existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Veterinario actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Veterinario no encontrado",
            content = @Content(mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "message": "Veterinario con ID 5 no encontrado",
                      "path": "/api/veterinarios/5",
                      "timestamp": "2025-11-04T21:10:00"
                    }
                """)))
    })
    public ResponseEntity<VeterinarioResponseDto> actualizarVeterinario(
            @Parameter(description = "ID del veterinario a actualizar", required = true)
            @PathVariable Long id,
            @RequestBody VeterinarioRequestDto requestDto) {

        // Se manda a actualizar y se devuelve la entidad modificada
        VeterinarioResponseDto actualizado = veterinarioServicio.actualizarVeterinario(id, requestDto);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            throw new RuntimeException("Veterinario con ID " + id + " no encontrado");
        }
    }

    // -----------------------------
    // ✅ Eliminar veterinario
    // -----------------------------
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar veterinario", description = "Elimina un veterinario del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Veterinario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Veterinario no encontrado",
            content = @Content(mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "message": "Veterinario con ID 3 no encontrado",
                      "path": "/api/veterinarios/3",
                      "timestamp": "2025-11-04T21:15:00"
                    }
                """)))
    })
    public ResponseEntity<Void> eliminarVeterinario(
            @Parameter(description = "ID del veterinario a eliminar", required = true)
            @PathVariable Long id) {

        // Se llama al servicio para eliminar y se valida el resultado
        String resultado = veterinarioServicio.eliminarVeterinario(id);
        if (resultado.contains("eliminado")) {
            return ResponseEntity.noContent().build(); // Devuelve 204 sin contenido
        } else {
            throw new RuntimeException("Veterinario con ID " + id + " no encontrado");
        }
    }
}
