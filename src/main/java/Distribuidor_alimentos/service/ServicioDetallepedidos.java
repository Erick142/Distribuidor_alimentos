package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.DetallePedido;
import Distribuidor_alimentos.model.Usuario;
import Distribuidor_alimentos.repository.RepoDetallepedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioDetallepedidos {
    @Autowired
    private RepoDetallepedidos repoDetallepedidos;

    public List<DetallePedido> encontrarPorUsuario(Usuario usuario){
        return repoDetallepedidos.findAllByUsuario(usuario);
    }
    public Optional<DetallePedido> encontrarPorId(Integer id){
        return repoDetallepedidos.findById(id);
    }
    public DetallePedido guardar(DetallePedido detallePedido){
        return repoDetallepedidos.save(detallePedido);
    }
    public void eliminarPorId(Integer id){
        repoDetallepedidos.deleteById(id);
    }

}
