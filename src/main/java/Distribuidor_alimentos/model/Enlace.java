package Distribuidor_alimentos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Enlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @OneToOne
    private Usuario distribuidor;
    @OneToOne
    private Usuario institucion;
    @Column
    private String estado;

    public Enlace(Usuario distribuidor, Usuario institucion, String estado) {
        this.distribuidor = distribuidor;
        this.institucion = institucion;
        this.estado = estado;
    }
}
