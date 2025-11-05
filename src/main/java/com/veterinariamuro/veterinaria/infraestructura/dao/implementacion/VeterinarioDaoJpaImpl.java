package com.veterinariamuro.veterinaria.infraestructura.dao.implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.veterinariamuro.veterinaria.dominio.model.Veterinario;
import com.veterinariamuro.veterinaria.infraestructura.dao.interfaces.IVeterinarioDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// Esta clase se encarga de manejar el acceso a datos de los veterinarios.
// Implementa la interfaz IVeterinarioDao y usa JPA para interactuar con la base de datos.
@Repository
public class VeterinarioDaoJpaImpl implements IVeterinarioDao {

    // EntityManager es el objeto que nos permite comunicarnos con la base de datos.
    // Se encarga de persistir, actualizar, eliminar y consultar entidades.
    @PersistenceContext
    private EntityManager em;

    // Método para guardar un nuevo veterinario en la base de datos.
    @Override
    @Transactional
    public void guardar(Veterinario veterinario) {
        // persist inserta una nueva entidad en la tabla correspondiente.
        this.em.persist(veterinario);
        // flush fuerza que los cambios se guarden de inmediato.
        this.em.flush();
    }

    // Método para obtener todos los veterinarios registrados.
    @Override
    @Transactional(readOnly = true)
    public List<Veterinario> obtenerTodos() {
        // Se hace una consulta JPQL que selecciona todos los registros de la entidad Veterinario.
        // El resultado se devuelve como una lista de objetos Veterinario.
        return this.em.createQuery("SELECT v FROM Veterinario v", Veterinario.class)
                      .getResultList();
    }

    // Método para buscar un veterinario por su ID.
    @Override
    @Transactional(readOnly = true)
    public Optional<Veterinario> obtenerPorId(Long id) {
        // find busca la entidad por su clave primaria (ID).
        // Optional se usa para manejar el caso en que no se encuentre ningún resultado.
        return Optional.ofNullable(this.em.find(Veterinario.class, id));
    }

    // Método para actualizar un veterinario existente.
    @Override
    @Transactional
    public void actualizar(Veterinario veterinario) {
        // merge actualiza los datos de un veterinario ya existente.
        // Si el veterinario no está en el contexto de persistencia, merge lo une.
        this.em.merge(veterinario);
    }

    // Método para eliminar un veterinario de la base de datos.
    @Override
    @Transactional
    public void eliminar(Veterinario veterinario) {
        // remove elimina una entidad del contexto de persistencia.
        // La entidad debe estar "adjunta" al EntityManager para poder ser eliminada.
        this.em.remove(veterinario);
    }

}
