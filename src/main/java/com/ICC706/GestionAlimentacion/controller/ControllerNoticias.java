package com.ICC706.GestionAlimentacion.controller;

import com.ICC706.GestionAlimentacion.DAO.NoticiaDAO;
import com.ICC706.GestionAlimentacion.model.Noticia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;

@Controller
public class ControllerNoticias {

    @Autowired
    private NoticiaDAO noticiaDAO;

    @GetMapping ("/crearnoticia")
    public String crearnoticia(){
        return "crearnoticia";
    }

    //creacion de noticias
    @PostMapping("/nuevaNoticia")
    public String publicarNoticia(
            @RequestParam(name = "titulo", required = true)String titulo,
            @RequestParam(name = "subtitulo", required = true)String subtitulo,
            @RequestParam(name = "cuerpo", required = true)String cuerpo,
            @RequestParam(name = "etiquetas")String etiquetas,
            @DateTimeFormat(pattern = "DD-MM-YYYY") Date fecha
            /*
            @RequestParam(name = "imagen",defaultValue = "null", required = true) MultipartFile imagen) throws IOException {
        byte[] bytesImg=imagen.getBytes();
        String base64 = Base64.getEncoder().encodeToString(bytesImg)
             */
    ){
        noticiaDAO.save(new Noticia(titulo,subtitulo,cuerpo,etiquetas,fecha));
        return "redirect:success";
    }

    @GetMapping("/vernoticia/{id}")
    public String vernoticia(Model model){
        return "verNoticia";
    }


}
