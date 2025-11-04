package com.veterinariamuro.veterinaria.infraestructura.dao.implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.veterinariamuro.veterinaria.dominio.model.Cliente;
import com.veterinariamuro.veterinaria.infraestructura.dao.interfaces.IClienteDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ClienteDaoJpaImpl implements IClienteDao {
    
    @PersistenceContext
    private EntityManager em; 
    // EntityManager es el que nos permite interactuar con la base de datos
    
    @Override
    @Transactional
    public void GuardarCliente(Cliente cliente) {
        // Guarda un cliente nuevo en la base de datos
        this.em.persist(cliente); // INSERT
        this.em.flush(); // fuerza que los cambios se guarden inmediatamente
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> obtenerTodos() {
        // Obtiene todos los clientes
        // readOnly = true porque solo estamos leyendo
        return this.em.createQuery("SELECT u FROM Cliente u ").getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> obtenerPorId(long cedula) {
        // Busca un cliente por su cédula
        // Devuelve Optional para manejar el caso en que no exista
        return Optional.ofNullable(this.em.find(Cliente.class, cedula));
    }

    @Override
    @Transactional
    public void actualizar(Cliente cliente) {
        // Actualiza los datos de un cliente existente
        this.em.merge(cliente); // merge combina los cambios con la base de datos
    }

    @Override
    @Transactional
    public void eliminarCliente(Cliente cliente) {
        // Elimina un cliente de la base de datos
        // El cliente debe estar "attached" al EntityManager
        this.em.remove(cliente);
    }
    
}
