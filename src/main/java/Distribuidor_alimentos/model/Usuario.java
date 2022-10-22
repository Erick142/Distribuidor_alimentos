package Distribuidor_alimentos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
@Entity(name = "Usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude={"idUsuario","password"})

@Table
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUsuario;
    private String nombre;
    private String email;
    private String password;
    private String direccion;
    private String region;
    private String comuna;
    private String ciudad;
}



