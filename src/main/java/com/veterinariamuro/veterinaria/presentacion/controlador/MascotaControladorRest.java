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
import com.veterinariamuro.veterinaria.aplicacion.Dto.clienteDto.ClienteResponseDto;
import com.veterinariamuro.veterinaria.aplicacion.Dto.mascotaDto.MascotaRequestDto;
import com.veterinariamuro.veterinaria.aplicacion.Dto.mascotaDto.MascotaResponseDto;
import com.veterinariamuro.veterinaria.aplicacion.interfaces.IClienteServicio;
import com.veterinariamuro.veterinaria.aplicacion.interfaces.IMascotaServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/mascotas")
@Tag(name = "Mascotas", description = "Operaciones relacionadas con las mascotas de la veterinaria")
public class MascotaControladorRest {

    @Autowired
    private IMascotaServicio mascotaServicio;

    @Autowired
    private IClienteServicio clienteServicio;

    // -----------------------
    // ✅ Listar todas las mascotas
    // -----------------------
    @GetMapping
    @Operation(summary = "Listar todas las mascotas", description = "Obtiene todas las mascotas registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    public ResponseEntity<List<MascotaResponseDto>> listarMascotas() {
        List<MascotaResponseDto> mascotas = mascotaServicio.obtenerTodas();
        return ResponseEntity.ok(mascotas);
    }

    // -----------------------
    // ✅ Obtener mascota por ID
    // -----------------------
    @GetMapping("/{id}")
    @Operation(summary = "Obtener mascota por ID", description = "Recupera la información de una mascota específica por su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mascota encontrada",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "id": 1,
                      "nombre": "Rocky",
                      "fechaNacimiento": "2020-05-12",
                      "sexo": "Macho",
                      "descripcion": "Perro activo y amigable",
                      "proximaFechaVacunacion": "2025-12-01",
                      "tipoAnimal": "Perro",
                      "clienteId": 1001,
                      "raza": "Labrador",
                      "colorPelaje": "Dorado"
                    }
                """))),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada",
            content = @Content(mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "message": "Mascota con ID 99 no encontrada",
                      "path": "/api/mascotas/99",
                      "timestamp": "2025-11-04T19:20:00"
                    }
                """)))
    })
    public ResponseEntity<MascotaResponseDto> obtenerMascota(
            @Parameter(description = "ID de la mascota", required = true)
            @PathVariable Long id) {

        MascotaResponseDto mascota = mascotaServicio.obtenerPorId(id);
        if (mascota != null) {
            return ResponseEntity.ok(mascota);
        } else {
            throw new RuntimeException("Mascota con ID " + id + " no encontrada");
        }
    }

    // -----------------------
    // ✅ Crear nueva mascota
    // -----------------------
    @PostMapping
    @Operation(summary = "Crear nueva mascota", description = "Registra una nueva mascota en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Mascota creada correctamente",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "id": 10,
                      "nombre": "Luna",
                      "fechaNacimiento": "2021-01-15",
                      "sexo": "Hembra",
                      "descripcion": "Gata doméstica cariñosa",
                      "proximaFechaVacunacion": "2026-01-15",
                      "tipoAnimal": "Gato",
                      "clienteId": 1002,
                      "raza": "Siamesa",
                      "colorPelaje": "Blanco"
                    }
                """))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
            content = @Content(mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(value = """
                    {
                      "status": 400,
                      "message": "El campo 'nombre' no puede estar vacío",
                      "path": "/api/mascotas",
                      "timestamp": "2025-11-04T19:22:00"
                    }
                """)))
    })
    public ResponseEntity<MascotaResponseDto> guardarMascota(@RequestBody MascotaRequestDto requestDto) {
        MascotaResponseDto creada = mascotaServicio.crearMascota(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // -----------------------
    // ✅ Actualizar mascota existente
    // -----------------------
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar mascota existente", description = "Actualiza los datos de una mascota registrada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mascota actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada",
            content = @Content(mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "message": "Mascota con ID 88 no encontrada",
                      "path": "/api/mascotas/88",
                      "timestamp": "2025-11-04T19:25:00"
                    }
                """)))
    })
    public ResponseEntity<MascotaResponseDto> actualizarMascota(
            @Parameter(description = "ID de la mascota", required = true)
            @PathVariable Long id,
            @RequestBody MascotaRequestDto requestDto) {

        MascotaResponseDto actualizada = mascotaServicio.actualizarMascota(id, requestDto);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        } else {
            throw new RuntimeException("Mascota con ID " + id + " no encontrada");
        }
    }

    // -----------------------
    // ✅ Eliminar mascota
    // -----------------------
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mascota", description = "Elimina una mascota del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Mascota eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada",
            content = @Content(mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ErrorResponse.class),
                examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "message": "Mascota con ID 15 no encontrada",
                      "path": "/api/mascotas/15",
                      "timestamp": "2025-11-04T19:30:00"
                    }
                """)))
    })
    public ResponseEntity<Void> eliminarMascota(
            @Parameter(description = "ID de la mascota", required = true)
            @PathVariable Long id) {

        String resultado = mascotaServicio.eliminarMascota(id);
        if (resultado.contains("eliminada")) {
            return ResponseEntity.noContent().build();
        } else {
            throw new RuntimeException("Mascota con ID " + id + " no encontrada");
        }
    }

    // -----------------------
    // ✅ Listar clientes relacionados (opcional)
    // -----------------------
    @GetMapping("/clientes")
    @Operation(summary = "Listar todos los clientes", description = "Obtiene todos los clientes registrados (para asignar mascotas)")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente")
    public ResponseEntity<List<ClienteResponseDto>> listarClientes() {
        List<ClienteResponseDto> clientes = clienteServicio.obtenerTodos();
        return ResponseEntity.ok(clientes);
    }
}
