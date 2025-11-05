package com.veterinariamuro.veterinaria.infraestructura.dao.interfaces;

import java.util.List;
import java.util.Optional;

import com.veterinariamuro.veterinaria.dominio.model.Veterinario;



public interface IVeterinarioDao {

    void guardar(Veterinario veterinario);

    List<Veterinario> obtenerTodos();

    Optional<Veterinario> obtenerPorId(Long id);

    void actualizar(Veterinario veterinario);

    void eliminar(Veterinario veterinario);
    
}
