package com.ICC706.GestionAlimentacion.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString(exclude={"id"})

@Entity(name = "Noticias")
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titulo;
    private String subtitulo;
    private String cuerpo;
    private String etiquetas;
    private Date fecha;
    @Lob
    private String imagen;

    public Noticia(String titulo, String subtitulo, String cuerpo, String etiquetas, Date fecha) {
        this.titulo=titulo;
        this.subtitulo=subtitulo;
        this.cuerpo=cuerpo;
        this.etiquetas=etiquetas;
        this.fecha=fecha;
        //this.imagen=base64;
    }
}
