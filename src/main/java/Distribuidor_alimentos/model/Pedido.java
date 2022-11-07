package Distribuidor_alimentos.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString(exclude = "id")

@Entity(name = "Pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private LocalDate fechaCreacion;
    private boolean rectificado;
    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_detalle_pedido_id")
    private DetallePedido id_detallePedido;
    //,DetallePedido id_detallePedido
    public Pedido(LocalDate fechaCreacion, Usuario usuario){
        this.fechaCreacion=fechaCreacion;
        this.usuario=usuario;
        this.rectificado=false;
        //this.id_detallePedido=id_detallePedido;
    }

}
