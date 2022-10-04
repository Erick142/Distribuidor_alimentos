package PA.distribuidor.model;

        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    private int id;
    private String tipo;
    private int cantidad;
    private String nota;
    private String fecha;
}
