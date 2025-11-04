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
import com.veterinariamuro.veterinaria.aplicacion.Dto.MascotaDto;
import com.veterinariamuro.veterinaria.aplicacion.interfaces.IClienteServicio;
import com.veterinariamuro.veterinaria.aplicacion.interfaces.IMascotaServicio;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaControladorRest {

    @Autowired
    private IMascotaServicio mascotaServicio;
    // Servicio que maneja la lógica de negocio de mascotas

    @Autowired
    private IClienteServicio clienteServicio;
    // Servicio que maneja la lógica de negocio de clientes (solo para listar)

    // ✅ Lista todas las mascotas
    @GetMapping
    public List<MascotaDto> listarMascotas() {
        // Retorna todas las mascotas en formato JSON
        return mascotaServicio.obtenerTodas();
    }

    // ✅ Obtener mascota por id
    @GetMapping("/{id}")
    public ResponseEntity<MascotaDto> obtenerMascota(@PathVariable Long id) {
        // Busca una mascota por su id
        MascotaDto mascota = mascotaServicio.obtenerPorId(id);
        if (mascota != null) {
            return ResponseEntity.ok(mascota); // Si existe, retorna 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Si no existe, 404
        }
    }

    // ✅ Crear nueva mascota
    @PostMapping
    public ResponseEntity<MascotaDto> guardarMascota(@RequestBody MascotaDto mascotaDto) {
        // Crea una nueva mascota usando los datos recibidos en JSON
        MascotaDto creada = mascotaServicio.crearMascota(mascotaDto);
        return new ResponseEntity<>(creada, HttpStatus.CREATED); 
        // Retorna 201 CREATED con la mascota creada
    }

    // ✅ Actualizar mascota
    @PutMapping("/{id}")
    public ResponseEntity<MascotaDto> actualizarMascota(@PathVariable Long id, @RequestBody MascotaDto mascotaDto) {
        // Actualiza la mascota indicada por id
        MascotaDto actualizada = mascotaServicio.actualizarMascota(id, mascotaDto);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada); // 200 OK si se actualizó
        } else {
            return ResponseEntity.notFound().build(); // 404 si no existe
        }
    }

    // ✅ Eliminar mascota
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Long id) {
        // Elimina la mascota por id
        String eliminado = mascotaServicio.eliminarMascota(id);
        if (eliminado != null) {
            return ResponseEntity.noContent().build(); // 204 No Content si se eliminó
        } else {
            return ResponseEntity.notFound().build(); // 404 si no existe
        }
    }

    // ✅ Lista todos los clientes (opcional)
    @GetMapping("/clientes")
    public List<ClienteDto> listarClientes() {
        // Permite ver todos los clientes (opcional, útil para relaciones mascota-cliente)
        return clienteServicio.obtenerTodos();
    }
}
