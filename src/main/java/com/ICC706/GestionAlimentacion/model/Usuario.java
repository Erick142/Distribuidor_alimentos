package com.ICC706.GestionAlimentacion.model;

import lombok.*;
import javax.persistence.*;
@Data
@ToString(exclude={"id","password"})
@NoArgsConstructor
@Entity(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String aPaterno;
    private String aMaterno;
    private String email;
    private String password;
    private String rol;
    /*
    private String direccion;
    private String region;
    private String comuna;
    private String ciudad;
     */

    private String nombreInstitucion;

    public Usuario(String nombre, String aPaterno, String aMaterno, String nombreInstitucion,String email, String password, String rol) {
        this.nombre=nombre;
        this.aPaterno=aPaterno;
        this.aMaterno=aMaterno;
        this.nombreInstitucion=nombreInstitucion;
        this.email=email;
        this.password=password;
        this.rol=rol;


    }
}

/*
public Usuario(String nombre, String aPaterno, String aMaterno, String nombreInstitucion,String email, String password, String direccion, String region, String comuna, String ciudad) {
    this.nombre=nombre;
        this.aMaterno=aMaterno;
        this.aPaterno=aPaterno;
        this.nombreInstitucion=nombreInstitucion;
        this.email=email;
        this.password=password;
        this.direccion=direccion;
        this.region=region;
        this.comuna=comuna;
        this.ciudad=ciudad;


                 }
 */



