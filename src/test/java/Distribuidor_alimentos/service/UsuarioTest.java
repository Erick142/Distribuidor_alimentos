package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioTest {
    @Autowired
    private ServicioUsuarios usuarios;
    @Autowired
    private ServicioMail mail;

    @ParameterizedTest
    @DisplayName("crear usuarios")
    @ValueSource(strings = {"erickkk@gmail.com","jorgeee@gmail.com","algo@gmail.com"})
    public void crearUsuario(String string){
        if (usuarios.existePorEmail(string)){
            usuarios.eliminar(usuarios.obtener(string));
        }
        usuarios.guardar(new Usuario("nombre",string,"password","distribuidor"));
        Usuario usuarioObtenido=usuarios.obtener(string);
        assertTrue(usuarios.existePorEmail(string));
        assertFalse(usuarioObtenido.getEmail().equals(123213));
        assertNotNull(usuarioObtenido);
        assertEquals("distribuidor",usuarioObtenido.getRoles());

    }
    @RepeatedTest(3)
    @DisplayName("encontrar por token")
    public void token(){
        Usuario usuario=new Usuario("nombre","erickkk@gmail.com","password","distribuidor");
        String token=mail.crearToken();
        usuario.setTokenPassword(token);
        usuarios.guardar(usuario);
        assertNotNull(usuarios.encontrarPorToken(token));
        assertEquals(token,usuarios.encontrarPorToken(token).get().getTokenPassword());
        assertFalse(!usuarios.encontrarPorToken(token).get().getEmail().equals("erickkk@gmail.com"));
        assertTrue(usuarios.encontrarPorToken(token).get().getNombre().equals("nombre"));
    }
    @Test
    @DisplayName("Eliminar Usuario")
    public void eliminar(){
        Usuario usuario=usuarios.guardar(new Usuario("nombre","eliminar@gmail.com","password","distribuidor"));
        usuarios.eliminar(usuario);
        assertFalse(usuarios.existePorEmail(usuario.getEmail()));
    }
}
