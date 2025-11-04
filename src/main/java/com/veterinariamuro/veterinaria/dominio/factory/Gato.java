package com.veterinariamuro.veterinaria.dominio.factory;

import java.time.LocalDate;

import com.veterinariamuro.veterinaria.dominio.model.Cliente;
import com.veterinariamuro.veterinaria.dominio.model.Mascota;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GATO") 
// Indica que esta clase es una subclase de Mascota en una estrategia de herencia SINGLE_TABLE
public class Gato extends Mascota {

    @Column(name = "color_pelaje", length = 50)
    private String colorPelaje; 
    // Atributo específico de los gatos para guardar el color del pelaje

    public Gato() {
        // Constructor vacío requerido por JPA para crear instancias mediante reflexión
    }

    public Gato(String nombre, LocalDate fechaNacimiento, String sexo, String descripcion,
                LocalDate proximaFechaVacunacion, Cliente cliente, String colorPelaje) {
        super(null, nombre, fechaNacimiento, sexo, descripcion, proximaFechaVacunacion, cliente);
        // Llamamos al constructor de la clase base Mascota
        this.colorPelaje = colorPelaje; 
        // Asignamos el color del pelaje del gato
    }

    public String getColorPelaje() {
        return colorPelaje; 
        // Getter para obtener el color del pelaje
    }

    public void setColorPelaje(String colorPelaje) {
        this.colorPelaje = colorPelaje; 
        // Setter para actualizar el color del pelaje
    }
}
