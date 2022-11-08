package Distribuidor_alimentos.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
//@ToString(exclude = "id")

@Entity(name = "Pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private LocalDate fechaCreacion;
    private LocalDate fechaEntrega;
    private boolean rectificado;
    @OneToOne
    private Usuario usuario;

    public Pedido(LocalDate fechaCreacion, Usuario usuario){
        this.fechaCreacion=fechaCreacion;
        this.usuario=usuario;
        this.rectificado=false;
    }

    public Pedido(LocalDate fechaCreacion, Usuario usuario,LocalDate fechaEntrega){
        this.fechaCreacion=fechaCreacion;
        this.usuario=usuario;
        this.rectificado=false;
        this.fechaEntrega=fechaEntrega;
    }

}
