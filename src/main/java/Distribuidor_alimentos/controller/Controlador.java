package Distribuidor_alimentos.controller;
import Distribuidor_alimentos.model.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;

@Controller
public class Controlador {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/index")
    public String index2(){
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/registro")
    public String registro(){
        return "registro";
    }
    @RequestMapping("/userhome")
    public String userhome(@RequestParam(name = "email", required = true, defaultValue = "usuario") String email,
                           @RequestParam(name = "password", required = true, defaultValue = "null") String pass,
                           Model model){
        model.addAttribute("email", email);
        model.addAttribute("password", pass);
        ArrayList<Registro> arrayList = new ArrayList<>();
        for (int i=0;i<5;i++){
            Registro registro=new Registro();
            registro.setFecha("04/10/2022");
            registro.setRectificado(true);
            Registro registro1=new Registro();
            registro1.setFecha("04/10/2022");
            registro1.setRectificado(false);
            arrayList.add(registro);
            arrayList.add(registro1);
        }
        model.addAttribute("list",arrayList);
        return "userhome";
    }
    @RequestMapping ("/homedistribuidor")
    public String homedistribuidor(@RequestParam(name = "email", required = true, defaultValue = "usuario") String email,
                                   @RequestParam(name = "password", required = true, defaultValue = "null") String pass,
                                   Model model){
        model.addAttribute("email", email);
        model.addAttribute("password", pass);
        ArrayList<Registro> arrayList = new ArrayList<>();
        for (int i=0;i<5;i++){
            Registro registro=new Registro();
            registro.setFecha("04/10/2022");
            registro.setRectificado(true);
            registro.setInstitucion("escuela1");
            Registro registro1=new Registro();
            registro1.setFecha("04/10/2022");
            registro1.setRectificado(false);
            registro1.setInstitucion("escuela2");
            arrayList.add(registro);
            arrayList.add(registro1);
        }
        model.addAttribute("list",arrayList);
        return "homedistribuidor";
    }
    @GetMapping("/vernoticia")
    public String vernoticia(Model model){
        String titulo="Titulo de ejemplo";
        String subtitulo="Este es el subtitulo de la noticia";
        String contenido="Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veritatis error ab perspiciatis illum natus distinctio nulla laborum vero suscipit qui a, eaque reiciendis saepe facere tempore, aliquam in voluptatibus sed.\n" +
                "                Cumque sunt eum ipsum nostrum! Quae consequuntur expedita pariatur delectus blanditiis at omnis ipsum debitis unde. Corporis magni magnam, pariatur molestias distinctio accusamus suscipit illo culpa. Veniam dolorum exercitationem cumque?\n" +
                "                Nihil vel quia, facilis maxime temporibus quo accusantium veniam dolor iusto aut fuga debitis harum laborum sint nam odit libero eum esse id, possimus quidem rerum totam saepe. Ut, dolore?\n" +
                "                Veniam in maiores, tempore ipsa ipsum nihil nesciunt error soluta eveniet adipisci voluptates vero totam debitis dignissimos cumque sed illum possimus excepturi sequi ad voluptatum? Nemo quibusdam commodi doloribus non.\n" +
                "                \n" +
                "                Lorem ipsum dolor sit, amet consectetur adipisicing elit. Similique tenetur laudantium aliquam provident natus suscipit dolor expedita nihil! Consequuntur aliquam facere vel itaque ad consequatur, numquam culpa eaque veniam est.\n" +
                "                Lorem ipsum, dolor sit amet consectetur adipisicing elit. Eligendi earum accusamus quasi distinctio, nam exercitationem ratione quibusdam dolorum libero alias ut veritatis consequatur atque necessitatibus, doloribus totam nihil eaque! Magni.\n" +
                "                Sit laborum ea exercitationem quod nisi voluptatum reiciendis unde recusandae minus alias, porro cum eaque velit laudantium, obcaecati sapiente asperiores doloremque ad iste. Error voluptatibus, ipsum molestias impedit molestiae laudantium!\n" +
                "                Et repellat molestias saepe sed earum atque similique aliquam dolorum soluta ullam nam, eveniet enim. Aspernatur, laborum error, sint ducimus voluptas nobis porro ipsa assumenda sed eum at, veritatis similique?\n" +
                "                Sunt dolorum beatae, id, asperiores voluptates nihil consequuntur sequi impedit ut obcaecati laboriosam odio. Atque ducimus, adipisci provident amet, reiciendis eligendi praesentium tempora perspiciatis, harum dignissimos unde! Nemo, explicabo asperiores.\n" +
                "                Sit numquam rerum explicabo unde sequi hic, tempore totam maiores culpa, deleniti nihil sapiente ab distinctio commodi iure quo incidunt repudiandae enim corrupti iste reiciendis quod soluta deserunt. Sint, sed!\n" +
                "                Iure itaque atque ea. Nobis dolores quaerat sed. Ea omnis excepturi velit dignissimos ratione, aliquid quos molestias ipsum dolores provident sed porro magni, quia illo maiores, debitis expedita aut! Eveniet?";
        model.addAttribute("contenido",contenido);
        model.addAttribute("titulo",titulo);
        model.addAttribute("subtitulo",subtitulo);
        return "vernoticia";
    }
    @RequestMapping ("/crearnoticia")
    public String crearnoticia(){
        return "crearnoticia";
    }
    @GetMapping("/nuevoPedido")
    public String nuevoPedido(){
        return "nuevoPedido";
    }
    @GetMapping("/success")
    public String success(){
        return "success";
    }
    @GetMapping("/rectificarPedido")
    public String rectificarPedido(){
        return "rectificarPedido";
    }
    @GetMapping("/estadisticas")
    public String estadisticas(){
        return "estadisticas";
    }
    @GetMapping("/error")
    public String error(){
        return "error";
    }
    @GetMapping("/exito")
    public String exito(){
        return "success";
    }
    @GetMapping("/confirmacion")
    public String confirmacionRegistro(){
        return "ConfirmacionRegistro";
    }
}
