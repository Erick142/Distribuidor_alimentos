package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.Noticia;
import Distribuidor_alimentos.repository.RepoNoticias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
@RequestMapping("/noticia")
@Controller
public class ControllerNoticias {
    @Autowired
    private RepoNoticias noticias;

    @GetMapping("/crear")
    public String crearnoticia(){
        return "crearnoticia";
    }

    @PostMapping("/subir")
    public String crear(@RequestParam(name = "titulo", required = true, defaultValue = "null") String titulo,
                        @RequestParam(name = "subtitulo", required = true, defaultValue = "null") String subtitulo,
                        @RequestParam(name = "cuerpo", defaultValue = "null",required = true) String cuerpo,
                        @RequestParam(name = "imagen",defaultValue = "null", required = true) MultipartFile imagen) throws IOException {
        byte[] bytesImg=imagen.getBytes();
        String base64 = Base64.getEncoder().encodeToString(bytesImg);
        noticias.save(new Noticia(titulo,subtitulo,cuerpo, base64));
        return "homedistribuidor";
    }
    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(name = "id")String id,
                      Model model){
        model.addAttribute("noticia",noticias.findById(Integer.parseInt(id)).get());
        return "vernoticia";
    }
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable(name = "id") String id,
                         Model model){
        model.addAttribute("noticia",noticias.findById(Integer.parseInt(id)).get());
        return "crearnoticia";
    }
}
