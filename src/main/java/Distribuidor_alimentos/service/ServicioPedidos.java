package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.model.Usuario;
import Distribuidor_alimentos.repository.RepoPedido;
import Distribuidor_alimentos.repository.RepoUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class ServicioPedidos implements RepoPedido {
    @Autowired
    private RepoUsuarios repo;

}
