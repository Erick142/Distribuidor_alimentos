package Distribuidor_alimentos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDate fecha;
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

    public Noticia(Integer id,String titulo, String subtitulo, String cuerpo, String base64,Usuario autor) {
        this.id=id;
        this.titulo=titulo;
        this.subtitulo=subtitulo;
        this.cuerpo=cuerpo;
        this.imagen=base64;
        this.autor=autor;
    }
}
