package com.veterinariamuro.veterinaria.dominio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;


@Builder
@Entity
@Table(name = "veterinarios")  
public class Veterinario {
    
    @Id
    @NotNull
    @Size(min = 9, max = 10)
    @Column(name = "cedula", nullable = false, length = 80)
    private Long cedula;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30) 
    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30) 
    @Column(name = "apellido1", nullable = false, length = 80)
    private String apellido1;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30) 
    @Column(name = "apellido2", nullable = false, length = 80)
    private String apellido2;

    @NotNull
    @Size(min = 9, max = 14)
    @Column(name = "telefono", nullable = false, length = 80)
    private int telefono;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 50) 
    @Column(name = "direccion", nullable = false, length = 80)
    private String direccion;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 40) 
    @Column(name = "correo", nullable = false, length = 80)
    private String correo;

    public Veterinario() {
    }

    public Veterinario(Long cedula, String nombre, String apellido1, String apellido2, int telefono, String direccion, String correo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Veterinario [cedula=" + cedula + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2="
                + apellido2 + ", telefono=" + telefono + ", direccion=" + direccion + ", correo=" + correo + "]";
    }

    


}
