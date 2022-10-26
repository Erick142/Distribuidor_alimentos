package Distribuidor_alimentos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Pedido implements Comparable<Pedido>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String tipo;
    private int cantidad;
    private String nota;
    private LocalDate fecha;
    @ManyToOne
    private Usuario usuario;
    @Column
    private boolean rectificado;

    public Pedido(String tipo, int cantidad, String nota, LocalDate fecha,Usuario usuario) {
        this.tipo = tipo;
        this.cantidad=cantidad;
        this.nota=nota;
        this.fecha=fecha;
        this.usuario=usuario;
        this.rectificado=false;
    }



    @Override
    public int compareTo(Pedido o) {
        if (this.id<o.getId()) return -1;
        else if (this.id==o.getId()) return 0;
        else return 1;
    }

}
