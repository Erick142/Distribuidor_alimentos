package Distribuidor_alimentos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registro {
    private String fecha;
    private boolean rectificado;
    private String institucion;
}
