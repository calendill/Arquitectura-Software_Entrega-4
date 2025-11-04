package com.veterinariamuro.veterinaria.dominio.eventos;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationEvent;

import com.veterinariamuro.veterinaria.aplicacion.Dto.ClienteDto;

public class ClienteEventos extends ApplicationEvent  {
    private final ClienteDto cliente;
    private final LocalDateTime fechaCreacion;
    // Guardamos el cliente afectado y la fecha de creación del evento

    public ClienteEventos(Object source, ClienteDto cliente ) {
        super(source); // source indica el origen del evento (quién lo disparó)
        this.cliente = cliente; // asignamos el cliente que generó el evento
        this.fechaCreacion = LocalDateTime.now(); 
        // Guardamos la fecha y hora en que se creó el evento
    }

    public ClienteDto getCliente() {
        return cliente; 
        // Método getter para obtener el cliente del evento
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion; 
        // Método getter para obtener la fecha de creación del evento
    }
}

    
