package com.veterinariamuro.veterinaria.dominio.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.experimental.SuperBuilder;



@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@SuperBuilder 
@Entity
@Table(name = "mascotas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_mascota")

public  class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String nombre;

    @PastOrPresent
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @NotBlank
    @Column(length = 10)
    private String sexo;

    @Column(length = 200)
    private String descripcion;

    @PastOrPresent
    @Column(name = "proxima_vacunacion")
    private LocalDate proximaFechaVacunacion;



    
    public Mascota() {
    }

    

    public Mascota(Long id, @NotBlank String nombre, @PastOrPresent LocalDate fechaNacimiento, @NotBlank String sexo,
            String descripcion, @PastOrPresent LocalDate proximaFechaVacunacion, Cliente cliente) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.descripcion = descripcion;
        this.proximaFechaVacunacion = proximaFechaVacunacion;
        this.cliente = cliente;
    }



    @JoinColumn(name = "cliente_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion; 
    }

    public LocalDate getProximaFechaVacunacion() {
        return proximaFechaVacunacion;
    }

    public void setProximaFechaVacunacion(LocalDate proximaFechaVacunacion) {
        this.proximaFechaVacunacion = proximaFechaVacunacion;
    }

  



    @Override
    public String toString() {
        return "Mascota [id=" + id + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + ", sexo=" + sexo
                + ", descripcion=" + descripcion + ", proximaFechaVacunacion=" + proximaFechaVacunacion
                + ", propietario=" + cliente + "]";
    }


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    

}
