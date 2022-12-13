package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.model.Usuario;
import Distribuidor_alimentos.repository.RepoPedido;
import Distribuidor_alimentos.repository.RepoUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuarios {
    @Autowired
    private RepoUsuarios repo;
    @Autowired
    private ServicioEnlace servicioEnlace;
    @Qualifier("repoPedido")
    @Autowired
    private RepoPedido repoPedidos;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public Usuario guardar(Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repo.save(usuario);
    }
    public boolean existePorEmail(String email){
        return repo.existsById(email);
    }
    public Usuario obtener(String email){
        return repo.findById(email).get();
    }
    public Optional<Usuario> encontrarPorToken(String token){
        return repo.findByTokenPassword(token);
    }
    public void eliminar(Usuario usuario){
        repo.delete(usuario);
    }

    public List<Pedido> encontrarLosPedidosDeMisInstituciones(Usuario distribuidor){
        List<Usuario> misInstitucionesAsociadas=servicioEnlace.obtenerMisInstituciones(distribuidor);
        List<Pedido> pedidosDeMisInstituciones=new ArrayList<>();
        for (Usuario miInstitucion:misInstitucionesAsociadas){
            pedidosDeMisInstituciones.addAll(repoPedidos.encontrarPorUsuario(miInstitucion));
        }
        return pedidosDeMisInstituciones;
    }
}
