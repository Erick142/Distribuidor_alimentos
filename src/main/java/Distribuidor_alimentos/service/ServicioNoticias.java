package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.Noticia;
import Distribuidor_alimentos.repository.RepoNoticias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioNoticias {
    @Autowired
    private RepoNoticias repoNoticias;
    public Noticia guardarNoticia(Noticia noticia){
        return repoNoticias.save(noticia);
    }
    public Noticia encontrarPorId(Integer id){
        return repoNoticias.findById(id).get();
    }
    public boolean existe(Integer id){
        return repoNoticias.existsById(id);
    }
    public List<Noticia> carrusel(int pageInt){
        Pageable page= PageRequest.of(pageInt,3);
        return repoNoticias.findAllByOrderByIdDesc(page);
    }
}
