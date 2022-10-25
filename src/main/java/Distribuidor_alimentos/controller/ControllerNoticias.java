package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.Noticia;
import Distribuidor_alimentos.repository.RepoNoticias;
import Distribuidor_alimentos.repository.RepoUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
@RequestMapping("/noticia")
@Controller
public class ControllerNoticias {
    @Autowired
    private RepoNoticias noticias;
    @Autowired
    private RepoUsuarios usuarios;

    @GetMapping("/crear")
    public String crearnoticia(Model model,
                               Principal principal){
        model.addAttribute("usuario",usuarios.findById(principal.getName()).get().getNombre());
        return "crearnoticia";
    }

    @PostMapping("/subir")
    public String crear(@RequestParam(name = "titulo", required = true, defaultValue = "null") String titulo,
                        @RequestParam(name = "subtitulo", required = true, defaultValue = "null") String subtitulo,
                        @RequestParam(name = "cuerpo", defaultValue = "null",required = true) String cuerpo,
                        @RequestParam(name = "imagen",defaultValue = "null", required = true) MultipartFile imagen,
                        Principal principal) throws IOException {
        byte[] bytesImg=imagen.getBytes();
        String base64 = Base64.getEncoder().encodeToString(bytesImg);
        noticias.save(new Noticia(titulo,subtitulo,cuerpo, base64,usuarios.findById(principal.getName()).get()));
        return "redirect:../home";
    }
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(name = "id")String id,
                      Model model,Principal principal){
        if (principal!=null){
            model.addAttribute("usuario",usuarios.findById(principal.getName()).get().getEmail());
        }
        Noticia noticia=noticias.findById(Integer.parseInt(id)).get();
        model.addAttribute("noticia",noticia);
        return "vernoticia";
    }
    @GetMapping("/editarnoticia")
    public String editarnoticia(@RequestParam(name = "id") String id,
                         Model model, Principal principal){
        if (!usuarios.findById(principal.getName()).get().getEmail().equals(noticias.findById(Integer.parseInt(id)).get().getAutor().getEmail())){
            return "redirect:http://localhost:8080/home";
        }
        model.addAttribute("noticia",noticias.findById(Integer.parseInt(id)).get());
        return "editarnoticia";
    }
    @PostMapping("/editar")
    public String editar(@RequestParam(name = "titulo", required = true, defaultValue = "null") String titulo,
                         @RequestParam(name = "subtitulo", required = true, defaultValue = "null") String subtitulo,
                         @RequestParam(name = "cuerpo", defaultValue = "null",required = true) String cuerpo,
                         @RequestParam(name = "imagen",defaultValue = "null", required = true) MultipartFile imagen,
                         @RequestParam(name = "id") String id,
                         Principal principal) throws IOException {
        if (!usuarios.findById(principal.getName()).get().getEmail().equals(noticias.findById(Integer.parseInt(id)).get().getAutor().getEmail())){
            return "redirect:http://localhost:8080/home";
        }
        Noticia noticia=noticias.findById(Integer.parseInt(id)).get();
        noticia.setCuerpo(cuerpo);
        noticia.setTitulo(titulo);
        noticia.setSubtitulo(subtitulo);
        byte[] bytesImg=imagen.getBytes();
        String base64 = Base64.getEncoder().encodeToString(bytesImg);
        noticia.setImagen(base64);
        noticias.save(noticia);
        return "redirect:http://localhost:8080/home";
    }
}
