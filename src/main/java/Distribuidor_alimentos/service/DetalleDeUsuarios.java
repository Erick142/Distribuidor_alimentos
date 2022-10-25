package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.UsuarioDeSeguridad;
import Distribuidor_alimentos.repository.RepoUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetalleDeUsuarios implements UserDetailsService {
    @Autowired
    private RepoUsuarios usuarios;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  usuarios
                .findById(username)
                .map(UsuarioDeSeguridad::new)
                .orElseThrow(()-> new UsernameNotFoundException("usuario no encontrado"));
    }

}
