package com.veterinariamuro.veterinaria.dominio.eventos;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.veterinariamuro.veterinaria.aplicacion.Dto.ClienteDto;

@Component
public class HandlerEventos {

    private long contadorClientes = 0;
    private LocalDateTime ultimaFechaCreacion;
    // Variables para llevar estadísticas: cantidad de clientes creados y fecha del último

    @EventListener
    public void onClienteCreado(ClienteEventos evento) {
        // Este método se ejecuta automáticamente cuando se dispara un ClienteEventos
        contadorClientes++; // Incrementa el contador de clientes
        ultimaFechaCreacion = evento.getFechaCreacion(); 
        // Guarda la fecha de creación del último cliente

        ClienteDto cliente = evento.getCliente(); 
        // Obtenemos el cliente del evento
        System.out.println("🐾 Cliente creado: " + cliente.getNombre() + " a las " + ultimaFechaCreacion);
        // Mostramos un mensaje en consola (útil para debug o verificación)
    }

    public long getContadorClientes() {
        return contadorClientes; 
        // Permite obtener cuántos clientes se han creado
    }

    public LocalDateTime getUltimaFechaCreacion() {
        return ultimaFechaCreacion; 
        // Permite obtener la fecha del último cliente creado
    }
}