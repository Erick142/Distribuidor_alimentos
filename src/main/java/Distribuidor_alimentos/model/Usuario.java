package Distribuidor_alimentos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

    @Column
    private String nombre;
    @Id
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String direccion;
    @Column
    private String region;
    @Column
    private String comuna;
    @Column
    private String ciudad;
    @Column
    private String roles;
    @Column
    private String tokenPassword;

    public boolean validarSesion(String email, String contraseña){
        return (this.email.equals(email)&&this.password.equals(contraseña))?true:false;
    }
    //constructor

    public Usuario(String nombre, String email, String password,String roles) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.roles=roles;
    }
}



