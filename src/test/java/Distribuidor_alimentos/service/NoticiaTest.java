package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.Noticia;
import Distribuidor_alimentos.model.Usuario;
import org.aspectj.weaver.ast.Not;
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
    @ValueSource(strings = {"titulo1", "tiltulo2", "tittulos42"})
    @DisplayName("Crear noticia")
    public void test1(String string) {
        Noticia noticia=noticias.guardarNoticia(new Noticia(string, "subtitulo", "cuerpo", null, null));
        Noticia noticiaobtenida=noticias.encontrarPorId(noticia.getId());
        assertEquals(string, noticiaobtenida.getTitulo());
        assertFalse(noticiaobtenida.getSubtitulo().equals("s"));
        assertNull(noticiaobtenida.getAutor());
        assertNotNull(noticiaobtenida);
    }

    @Test
    @DisplayName("actualizar noticia")
    public void test3() {
        Noticia noticia = noticias.guardarNoticia(new Noticia("q", "s,", "s", null, null));
        Integer id = noticia.getId();
        noticia.setTitulo("actualizado");
        noticia.setSubtitulo("actulizado");
        noticias.guardarNoticia(noticia);
        Noticia noticiaAActualizar=noticias.encontrarPorId(id);
        assertEquals("actualizado", noticiaAActualizar.getTitulo());
        assertFalse(noticiaAActualizar.getSubtitulo().equals("q"));
        assertNotNull(noticiaAActualizar);
        assertTrue(!noticiaAActualizar.getTitulo().equals("q"));
    }

    @Test
    @DisplayName("carrusel")
    public void test2() {
        Noticia n1 = noticias.guardarNoticia(new Noticia("titulo1", "sub1", "con1", null, null));
        Noticia n2 = noticias.guardarNoticia(new Noticia("titulo2", "sub1", "con1", null, null));
        Noticia n3 = noticias.guardarNoticia(new Noticia("titulo3", "sub1", "con1", null, null));
        List<Noticia> carrusel = noticias.carrusel(0);
        assertNotNull(carrusel);
        assertEquals("titulo3", carrusel.get(0).getTitulo());
        assertTrue(carrusel.size() == 3);
        assertFalse(carrusel.size() != 3);
        noticias.eliminar(n1);
        noticias.eliminar(n2);
        noticias.eliminar(n3);
    }

}
