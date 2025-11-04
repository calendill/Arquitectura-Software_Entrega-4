package com.veterinariamuro.veterinaria.presentacion.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veterinariamuro.veterinaria.aplicacion.Dto.EventoDto;
import com.veterinariamuro.veterinaria.dominio.eventos.HandlerEventos;

@RestController
@RequestMapping("/api/clientes") 
@CrossOrigin(origins = "*") 
// Permite que cualquier front-end (origen) pueda hacer peticiones a este controlador
public class EstadisticasController {

    @Autowired
    private HandlerEventos eventListener; 
    // Inyectamos un listener que maneja eventos y guarda estadísticas

    @GetMapping("/estadisticas")
    public EventoDto obtenerEstadisticas() {
        // Retorna un objeto DTO con las estadísticas
        // Llama al eventListener para obtener:
        // - la cantidad total de clientes creados
        // - la fecha de creación del último cliente
        return new EventoDto(
            eventListener.getContadorClientes(),
            eventListener.getUltimaFechaCreacion()
        );
    }
}

