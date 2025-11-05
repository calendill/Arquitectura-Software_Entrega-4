package com.veterinariamuro.veterinaria.dominio.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.experimental.SuperBuilder;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cedula"
)

@SuperBuilder
@Entity 
@Table(name = "clientes")  
public class Cliente {

    @Id
    @NotNull
    @Min(1000000)   // mínimo 7 dígitos
    @Max(999999999999L) // máximo 12 dígitos
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
    @Column(name = "apellido_1", nullable = false, length = 80)
    private String apellido1;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 30) 
    @Column(name = "apellido_2", nullable = false, length = 80)
    private String apellido2;

    @NotNull
    @Min(1000000)   // mínimo 7 dígitos
    @Max(999999999999L) // máximo 12 dígitos>
    @Column(name = "telefono", nullable = false, length = 80)
    private Long telefono;

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

    
    public Cliente() {
         mascotas = new ArrayList<>();
    }

    
    @OneToMany(mappedBy = "cliente", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Mascota> mascotas;



    public Cliente(@NotNull @Min(7) @Max(12) Long cedula, @NotNull @NotBlank @Size(min = 3, max = 30) String nombre,
            @NotNull @NotBlank @Size(min = 3, max = 30) String apellido1,
            @NotNull @NotBlank @Size(min = 3, max = 30) String apellido2, @NotNull @Min(8) @Max(12) Long telefono,
            @NotNull @NotBlank @Size(min = 10, max = 50) String direccion,
            @NotNull @NotBlank @Size(min = 6, max = 40) String correo) {
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

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
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
        return "Cliente [cedula=" + cedula + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2="
                + apellido2 + ", telefono=" + telefono + ", direccion=" + direccion + ", correo=" + correo + "]";
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    

    
}
