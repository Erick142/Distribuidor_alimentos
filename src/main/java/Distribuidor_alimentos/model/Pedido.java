package Distribuidor_alimentos.model;

        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import javax.persistence.Entity;
        import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {
    private String tipo;
    private int cantidad;
    private String nota;
    private String fecha;
    @Id
    private int id;
}
