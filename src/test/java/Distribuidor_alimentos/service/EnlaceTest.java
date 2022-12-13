package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.Enlace;
import Distribuidor_alimentos.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class EnlaceTest {
    @Autowired
    private ServicioEnlace servicioEnlace;
    @Autowired
    private ServicioUsuarios servicioUsuarios;
    @BeforeEach
    public void before(){
        if (!servicioUsuarios.existePorEmail("dis@gmail")){
            servicioUsuarios.guardar(new Usuario("dis","dis@gmail","pass","distribuidor"));
        }
        if (!servicioUsuarios.existePorEmail("ins@gmail")){
            servicioUsuarios.guardar(servicioUsuarios.obtener("ins@gmail"));
        }
    }
    @Test
    @DisplayName("crear")
    public void crear(){

        Usuario institucion=servicioUsuarios.obtener("ins@gmail");
        Usuario distribuidor=servicioUsuarios.obtener("dis@gmail");
        Enlace enlace=new Enlace(distribuidor,institucion,"en espera");
        servicioEnlace.guardarEnlace(enlace);
        assertTrue(servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,institucion).isPresent());
        assertEquals("en espera",servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,institucion).get().getEstado());
        assertFalse(servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,institucion).get().getDistribuidor().equals(institucion));
        assertNotEquals("aceptada",servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,institucion).get().getEstado());
        servicioEnlace.eliminarEnlace(enlace);

    }
    @Test
    @DisplayName("eliminar enlace")
    public void eliminar(){
        Usuario institucion=servicioUsuarios.obtener("ins@gmail");
        Usuario distribuidor=servicioUsuarios.obtener("dis@gmail");
        Enlace enlace=new Enlace(distribuidor,institucion,"en espera");
        servicioEnlace.guardarEnlace(enlace);
        servicioEnlace.eliminarEnlace(enlace);
        assertFalse(servicioEnlace.encontrarPorId(enlace.getId()).isPresent());
    }
    @Test
    @DisplayName("obtener enlaces")
    public void obtener(){
        Usuario institucion=servicioUsuarios.obtener("ins@gmail");
        Usuario distribuidor=servicioUsuarios.obtener("dis@gmail");
        Enlace enlaces=new Enlace(distribuidor,institucion,"en espera");
        servicioEnlace.guardarEnlace(enlaces);
        Optional<Enlace> enlace=servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,institucion);
        Optional<Enlace> enlace2=servicioEnlace.encontrarEnlacePorInstitucion(institucion);
        List<Enlace> enlace3=servicioEnlace.encontrarEnlacesPorDistribuidorYEstado(distribuidor,"acepatado");
        assertTrue(enlace.isPresent());
        assertTrue(enlace3.isEmpty());
        assertNotNull(enlace2);
        servicioEnlace.eliminarEnlace(enlaces);
    }
}
