package Distribuidor_alimentos.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude = "id")

@Entity(name = "Detalle_pedidos")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    private String tipo;
    private int cantidad;
    private String nota;

    @ManyToOne
    @JoinColumn(name = "usuario_email")
    private Usuario usuario;


    //Pedido pedido,Menu menu
    public DetallePedido(String tipo, int cantidad, String nota,Menu menu,Pedido pedido) {
        this.tipo =tipo;
        this.cantidad=cantidad;
        this.nota=nota;
        this.menu=menu;
        this.pedido=pedido;
    }

    public DetallePedido(String tipo, int cantidad, String nota,Pedido pedido,Usuario usuario) {
        this.tipo =tipo;
        this.cantidad=cantidad;
        this.nota=nota;
        this.pedido=pedido;
        this.usuario=usuario;
    }

}
