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
    @AfterAll
    public void after(){
        if (servicioUsuarios.existePorEmail("dis@gmail")){
            servicioUsuarios.eliminar(servicioUsuarios.obtener("dis@gmail"));
        }
        if (servicioUsuarios.existePorEmail("ins@gmail")){
            servicioUsuarios.eliminar(servicioUsuarios.obtener("ins@gmail"));
        }
    }
    @BeforeAll
    public void aft(){
        servicioUsuarios.guardar(new Usuario("dis","dis@gmail","pass","distribuidor"));
        servicioUsuarios.guardar(new Usuario("ins","ins@gmail","pass","institucion"));
    }
    @Test
    @DisplayName("crear")
    public void crear(){
        Usuario institucion=servicioUsuarios.obtener("ins@gmail");
        Usuario distribuidor=servicioUsuarios.obtener("dis@gmail");
        Enlace enlace=new Enlace(distribuidor,institucion,"en espera");
        assertTrue(servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,institucion).isPresent());
        assertEquals("en espera",servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,institucion).get().getEstado());
        assertFalse(servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,institucion).get().getDistribuidor().equals(institucion));
        assertNotEquals("aceptada",servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,institucion).get().getEstado());
    }
    @Test
    @DisplayName("eliminar enlace")
    public void eliminar(){
        Usuario institucion=servicioUsuarios.obtener("ins@gmail");
        Usuario distribuidor=servicioUsuarios.obtener("dis@gmail");
        servicioEnlace.eliminarEnlace(servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,institucion).get());
        assertFalse(servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,institucion).isPresent());
    }
    @Test
    @DisplayName("obtener enlaces")
    public void obtener(){
        Usuario institucion=servicioUsuarios.obtener("ins@gmail");
        Usuario distribuidor=servicioUsuarios.obtener("dis@gmail");
        Optional<Enlace> enlace=servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,institucion);
        Optional<Enlace> enlace2=servicioEnlace.encontrarEnlacePorInstitucion(institucion);
        List<Enlace> enlace3=servicioEnlace.encontrarEnlacesPorDistribuidorYEstado(distribuidor,"en espera");
        assertFalse(enlace.isPresent());
        assertTrue(enlace3.isEmpty());
        assertNotNull(enlace2);
    }
}
