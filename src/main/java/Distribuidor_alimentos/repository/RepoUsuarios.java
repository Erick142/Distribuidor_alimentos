package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoUsuarios extends CrudRepository<Usuario,String> {
    List<Usuario> findAllByTokenPassword(String token);
}