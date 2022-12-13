package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.Usuario;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ServicioMail {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ServicioUsuarios usuarios;

    public void enviarEmail(String toEmail){
        String token=crearToken();
        SimpleMailMessage mensaje=crearEmail(toEmail,token);
        Usuario usuario=usuarios.obtener(toEmail);
        usuario.setTokenPassword(token);
        usuarios.guardar(usuario);
        mailSender.send(mensaje);
    }
    public String crearToken(){
        String token;
        do {
            token= RandomString.make(30);
        }while (usuarios.encontrarPorToken(token).isPresent());
        return token;
    }
    public SimpleMailMessage crearEmail(String toEmail, String token){
        SimpleMailMessage mensaje=new SimpleMailMessage();
        mensaje.setFrom("distribuidordealimentopa@gmail.com");
        mensaje.setTo(toEmail);
        mensaje.setText("http://localhost:8080/usuario/cambiar_contraseña?token="+token);
        mensaje.setSubject("recuperar contraseña");
        return mensaje;
    }
}
