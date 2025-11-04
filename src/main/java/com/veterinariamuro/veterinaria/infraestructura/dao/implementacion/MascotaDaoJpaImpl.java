package com.veterinariamuro.veterinaria.infraestructura.dao.implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.veterinariamuro.veterinaria.dominio.model.Mascota;
import com.veterinariamuro.veterinaria.infraestructura.dao.interfaces.IMascotaDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class MascotaDaoJpaImpl implements IMascotaDao {

    @PersistenceContext
    private EntityManager em; 
    // EntityManager nos permite hacer operaciones en la base de datos (persistir, actualizar, eliminar, buscar)

    @Override
    @Transactional
    public void guardar(Mascota mascota) {
        // Guarda una mascota nueva en la base de datos
        this.em.persist(mascota); // INSERT
        this.em.flush(); // fuerza que los cambios se guarden inmediatamente
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mascota> obtenerTodas() {
        // Devuelve todas las mascotas
        // readOnly = true porque solo estamos leyendo, no modificando
        return this.em.createQuery("SELECT m FROM Mascota m", Mascota.class)
                      .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Mascota> obtenerPorId(Long id) {
        // Busca una mascota por su id
        // Optional para manejar el caso de que no exista
        return Optional.ofNullable(this.em.find(Mascota.class, id));
    }

    @Override
    @Transactional
    public void actualizar(Mascota mascota) {
        // Actualiza una mascota existente
        this.em.merge(mascota); // merge combina los cambios con la base de datos
    }

    @Override
    @Transactional
    public void eliminar(Mascota mascota) {
        // Elimina una mascota de la base de datos
        // La mascota debe estar "attached" al EntityManager
        this.em.remove(mascota);
    }
}

