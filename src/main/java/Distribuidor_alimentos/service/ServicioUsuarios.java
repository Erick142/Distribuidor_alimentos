package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.Usuario;
import Distribuidor_alimentos.repository.RepoUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuarios {
    @Autowired
    private RepoUsuarios repo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public void guardar(Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        repo.save(usuario);
    }
}
