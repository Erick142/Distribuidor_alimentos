package com.ICC706.GestionAlimentacion.DAO;

import com.ICC706.GestionAlimentacion.model.Pedido;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PedidoDAO extends CrudRepository<Pedido,Long> {
}
