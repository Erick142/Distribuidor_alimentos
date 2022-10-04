package PA.distribuidor.model;

public class Registro {
    private String fecha;
    private boolean rectificado;
    private String institucion;

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setRectificado(boolean rectificado) {
        this.rectificado = rectificado;
    }

    public String getInstitucion() {
        return institucion;
    }

    public String getFecha() {
        return fecha;
    }

    public boolean isRectificado() {
        return rectificado;
    }
}
