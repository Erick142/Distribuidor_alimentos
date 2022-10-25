package com.ICC706.GestionAlimentacion.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public abstract class PedidoDAOimp implements PedidoDAO{

    private EntityManager entityManager;
    @Autowired
    private PedidoDAO pedidoDAO;
}
