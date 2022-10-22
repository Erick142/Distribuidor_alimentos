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
@Table(name = "Noticias")
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID noticia")
    private Long idNoticia;
    private String titulo;
    private String subtitulo;
    private String cuerpo;
    private String etiquetas;
    private Date fecha;
}
