package Distribuidor_alimentos.service;

import Distribuidor_alimentos.repository.RepoPedido;
import Distribuidor_alimentos.repository.RepoUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public abstract class ServicioPedidos implements RepoPedido {
    @Autowired
    private RepoUsuarios repo;
}
