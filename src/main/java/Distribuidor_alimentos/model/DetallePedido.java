package Distribuidor_alimentos.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString(exclude = "id")

@Entity(name = "Detalle_pedidos")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "tipo_id")
    private Menu menu;
    private String tipo;
    private int cantidad;
    private String nota;
    private LocalDate fechaEntrega;
    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    //Pedido pedido,Menu menu
    public DetallePedido(String tipo, int cantidad, String nota, LocalDate fechaEntrega, Usuario usuario) {
        this.tipo =tipo;
        this.cantidad=cantidad;
        this.nota=nota;
        this.fechaEntrega=fechaEntrega;
        this.usuario=usuario;
    }

    public DetallePedido(String tipo, int cantidad, String nota, LocalDate fechaEntrega) {
        this.tipo =tipo;
        this.cantidad=cantidad;
        this.nota=nota;
        this.fechaEntrega=fechaEntrega;
    }

}
