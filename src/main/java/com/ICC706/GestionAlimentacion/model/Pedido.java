package com.ICC706.GestionAlimentacion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString(exclude={"id"})

@Entity(name = "Pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tipo;
    private int cantidad;
    private String nota;
    private Date fecha;

    public Pedido(String tipo, int cantidad, String nota, Date fecha) {
        this.tipo = tipo;
        this.cantidad=cantidad;
        this.nota=nota;
        this.fecha=fecha;
    }
}
