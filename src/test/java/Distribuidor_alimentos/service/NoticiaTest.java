package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.Noticia;
import Distribuidor_alimentos.model.Usuario;
import org.hibernate.annotations.Source;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NoticiaTest {
    @Autowired
    private ServicioNoticias noticias;
    @ParameterizedTest
    @ValueSource(strings={"titulo1","tiltulo2","tittulos42"}, ints = {99,100,101})
    @DisplayName("Crear noticia y carrusel")
    public void test1(String string,int ints){
        LocalDate date=LocalDate.now();
        noticias.guardarNoticia(new Noticia(ints,string,"subtitulo","cuerpo",date,null,null));
        Noticia noticiaobtenida=noticias.encontrarPorId(ints);
        assertEquals(string,noticiaobtenida.getTitulo());
        assertFalse(noticiaobtenida.getSubtitulo().equals("s"));
        assertTrue(noticiaobtenida.getFecha().equals(date));
        assertNotNull(noticiaobtenida);
    }
    @Test
    @DisplayName("actualizar")
    public void test3(){
        Noticia noticiaAActualizar=noticias.guardarNoticia(new Noticia("q","s,","s",null,null));
        Integer id=noticiaAActualizar.getId();
        noticiaAActualizar.setTitulo("actualizado");
        noticiaAActualizar.setSubtitulo("actulizado");
        noticias.guardarNoticia(noticiaAActualizar);
        assertEquals("actualizado",noticiaAActualizar.getTitulo());
        assertFalse(noticiaAActualizar.getSubtitulo().equals("q"));
        assertNotNull(noticiaAActualizar);
        assertTrue(!noticiaAActualizar.getTitulo().equals("q"));
    }
    @BeforeEach
    public void before(){
        noticias.guardarNoticia(new Noticia(999,"titulo1","sub1","con1",null,null,null));
        noticias.guardarNoticia(new Noticia(998,"titulo2","sub1","con1",null,null,null));
        noticias.guardarNoticia(new Noticia(997,"titulo3","sub1","con1",null,null,null));
    }
    @Test
    @DisplayName("carrusel")
    public void test2(){
        List<Noticia> carrusel=noticias.carrusel(0);
        assertNotNull(carrusel);
        assertEquals("titulo1",carrusel.get(0).getTitulo());
        assertTrue(carrusel.size()==3);
        assertFalse(carrusel.size()!=3);
    }
    @AfterEach
    public void after(){
        noticias.eliminar(noticias.encontrarPorId(999));
        noticias.eliminar(noticias.encontrarPorId(998));
        noticias.eliminar(noticias.encontrarPorId(997));
    }

}
