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
        SimpleMailMessage mensaje=new SimpleMailMessage();
        mensaje.setFrom("distribuidordealimentopa@gmail.com");
        mensaje.setTo(toEmail);

        String token;
        do {
            token= RandomString.make(30);
        }while (!usuarios.encontrarPorToken(token).isEmpty());
        Usuario usuario=usuarios.obtener(toEmail);
        usuario.setTokenPassword(token);
        mensaje.setText("http://localhost:8080/cambiar_contraseña?token="+token);
        usuarios.guardar(usuario);
        mensaje.setSubject("recuperar contraseña");
        mailSender.send(mensaje);
    }
}
