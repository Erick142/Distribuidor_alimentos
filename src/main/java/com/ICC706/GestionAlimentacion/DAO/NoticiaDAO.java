package com.ICC706.GestionAlimentacion.DAO;

import com.ICC706.GestionAlimentacion.model.Noticia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaDAO extends CrudRepository<Noticia,Long> {
}
