package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPedidos extends CrudRepository<Pedido,Integer> {
    List<Pedido> findByUsuario(Usuario usuario);
}
