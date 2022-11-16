package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.Noticia;
import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface RepoNoticias extends PagingAndSortingRepository<Noticia,Integer> {

    List<Noticia> findAllByOrderByIdDesc(Pageable pageable);


}
