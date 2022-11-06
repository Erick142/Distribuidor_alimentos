package Distribuidor_alimentos.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@ToString(exclude = "id")

@Entity(name = "Pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tipo;
    private int cantidad;
    private String nota;
    private LocalDate fecha;
    private boolean rectificado;
    @ManyToOne
    private Usuario usuario;

    public Pedido(String tipo, int cantidad, String nota, LocalDate fecha, Usuario usuario) {
        this.tipo = tipo;
        this.cantidad=cantidad;
        this.nota=nota;
        this.fecha=fecha;
        this.usuario=usuario;
        this.rectificado=false;
    }

}
