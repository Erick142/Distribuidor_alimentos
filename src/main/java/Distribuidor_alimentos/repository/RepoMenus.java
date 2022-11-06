package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepoMenus extends CrudRepository<Menu,Integer> {
    //@Query(value = "SELECT * FROM Menu m WHERE m.institucion_email='empresa@distribuidor.cl'", nativeQuery = true)
    @Query(value = "SELECT * FROM Menu m WHERE m.institucion_email=?1", nativeQuery = true)
    public List<Menu> encontrarPorUsuario(String email);

}
