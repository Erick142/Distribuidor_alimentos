package Distribuidor_alimentos.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Data
@NoArgsConstructor
@ToString(exclude = "id")

@Entity(name = "Menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    @Lob
    private String icono;
    @OneToOne
    private Usuario institucion;

    public Menu(String nombre, String descripcion,String icono,Usuario institucion) {
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.icono=icono;
        this.institucion=institucion;
    }
}


