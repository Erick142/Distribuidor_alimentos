package com.ICC706.GestionAlimentacion.DAO;

import com.ICC706.GestionAlimentacion.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UsuarioDAO extends CrudRepository<Usuario,Long> {
}