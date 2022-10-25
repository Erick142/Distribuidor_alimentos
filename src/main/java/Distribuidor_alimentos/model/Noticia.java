package Distribuidor_alimentos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor


@Entity
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private String titulo;
    @Column
    private String subtitulo;
    @Lob
    @Column
    private String cuerpo;
    @Column
    private String etiquetas;
    @Column
    private Date fecha;
    @Lob
    private String imagen;
    @ManyToOne
    private Usuario autor;

    public Noticia(String titulo, String subtitulo, String cuerpo, String base64,Usuario autor) {
        this.titulo=titulo;
        this.subtitulo=subtitulo;
        this.cuerpo=cuerpo;
        this.imagen=base64;
        this.autor=autor;
    }
}
