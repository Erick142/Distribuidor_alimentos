package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.*;
import Distribuidor_alimentos.repository.RepoEnlaces;
import Distribuidor_alimentos.repository.RepoMenus;
import Distribuidor_alimentos.repository.RepoDetallepedidos;
import Distribuidor_alimentos.repository.RepoPedido;
import Distribuidor_alimentos.service.ServicioDetallepedidos;
import Distribuidor_alimentos.service.ServicioUsuarios;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class ControladorPedidos {
    @Autowired
    private ServicioDetallepedidos servicioDetallepedidos;
    @Autowired
    private ServicioUsuarios servicioUsuarios;
    @Autowired
    private RepoMenus repoMenus;
    @Autowired
    private RepoEnlaces repoEnlaces;
    @Autowired
    private RepoPedido repoPedido;

    @GetMapping("/pedidos")
    public void listarPedidos(Model model, Principal principal) {
        Usuario usuarioActual = obtenerUsuarioActual(principal);
        model.addAttribute("pedidos", servicioDetallepedidos.encontrarPorUsuario(usuarioActual));
    }

    @GetMapping("/pedido/{id}")
    public Optional<DetallePedido> buscarPorID(@PathVariable int id) {
        return servicioDetallepedidos.encontrarPorId(id);
    }

    int id;
    @GetMapping("/nuevoPedido")
    public String nuevoPedido(Model model, Principal principal){
        listarMenus(model, principal);
        listarDetallePedido(model,principal);
        Pedido a = new Pedido();
        repoPedido.save(a);
        DetallePedido b = new DetallePedido();
        servicioDetallepedidos.guardar(b);
        System.out.println(a.toString());
        System.out.println(b.toString());
        id=a.getId();
        System.out.println("id :"+id);
        a.setId_detallePedido(b);
        return "nuevoPedido";
    }



    @PostMapping("/agregaralista")
    public String agregarAlista(
            @RequestParam(name = "tipo") String tipo,
            @RequestParam(name = "cantidad")int cantidad,
            @RequestParam(name = "nota")String nota,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model,
            Principal principal
    ){
        /*
        List<DetallePedido> listaDetallepedido = new ArrayList<>();
        DetallePedido detallepedidoAux = new DetallePedido(tipo,cantidad,nota,fecha,servicioUsuarios.obtener(principal.getName()));
        listaDetallepedido.add(detallepedidoAux);
        System.out.println(detallepedidoAux);
        System.out.println(listaDetallepedido);
        //model.addAttribute("pedidos", listaDetallepedido);
        */ //manejo con arraylist

        listarDetallePedido(model,principal);
        Usuario usuarioActual=obtenerUsuarioActual(principal);
        //System.out.println("id Pedido:"+id);
        //Pedido pedidoActual=repoPedido.findById(this.id).get();
        servicioDetallepedidos.guardar(new DetallePedido(tipo,cantidad,nota,fecha));
        return "redirect:/nuevoPedido";
    }

    @GetMapping("/listardetalle")
    public String listarDetallePedido(Model model,Principal principal){
        Usuario usuarioActual = obtenerUsuarioActual(principal);
        List lista_detalle =servicioDetallepedidos.encontrarPorUsuario(usuarioActual);
        //System.out.println("institucion : "+usuarioActual);
        //System.out.println("lista : "+lista_detalle);
        model.addAttribute("detallepedidos",lista_detalle );
        return "redirect:/nuevoPedido";
    }

    @PostMapping("/hacerpedido")
    public String hacerPedido(
            //@RequestParam(name = "fecha")
            //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCreacion,
            Model model,
            Principal principal
    ){
        listarMenus(model,principal);
        //,id_detallePedido
        Usuario usuarioActual = obtenerUsuarioActual(principal);
        //DetallePedido detallePedido = repoDetallepedidos.findById(id).get();
        Pedido pedidoActual=repoPedido.findById(id).get();
        LocalDate fechaCreacion = LocalDate.now();
        pedidoActual.setFechaCreacion(fechaCreacion);
        pedidoActual.setUsuario(usuarioActual);
        repoPedido.save(new Pedido(fechaCreacion,usuarioActual));
        return "redirect:/home";
    }


    @PutMapping(value="/rectificarPedido")
    public String editarPedido(@PathVariable(value = "id") int id,
                                              @RequestParam(name = "tipo") String tipo,
                                              @RequestParam(name = "cantidad") int cantidad,
                                              @RequestParam(name = "nota") String nota,
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                              Model model
    ){
        model.addAttribute("pedido", servicioDetallepedidos.encontrarPorId(id));
        DetallePedido detallepedidoAEditar = servicioDetallepedidos.encontrarPorId(id).get();
        detallepedidoAEditar.setTipo(tipo);
        detallepedidoAEditar.setCantidad(cantidad);
        detallepedidoAEditar.setNota(nota);
        detallepedidoAEditar.setFechaEntrega(fecha);
        servicioDetallepedidos.guardar(detallepedidoAEditar);
        return "redirect:home";
    }

    //Eliminar pedido
    @GetMapping("/borrarpedido/{id}")
    public String borrarPedido(@PathVariable(value = "id") int id) {
        if (servicioDetallepedidos.encontrarPorId(id).isPresent()
                && !servicioDetallepedidos.encontrarPorId(id).get().getFechaEntrega().isBefore(LocalDate.now())){
        servicioDetallepedidos.eliminarPorId(id);}
        else{return "redirect:error";}
        return "redirect:confirmacion";
    }


    //Estadísticas
    @GetMapping("/estadisticas/{id}")
    public String verEstadicticas(@PathVariable(value = "id") int id, Model model,Principal principal) {
        model.addAttribute("pedidoMasReciente", repoPedido.findById(id));
        model.addAttribute("pedidoAnterior", repoPedido.pedidoAnterior(obtenerUsuarioActual(principal)));
        return "estadisticas";
    }


    //Sección para añadir un tipo de menú nuevo
    @GetMapping("/vermenus")
    public String listarMenus(Model model, Principal principal) {
        try {
            Usuario usuarioActual = servicioUsuarios.obtener(principal.getName());
            if (usuarioActual.getRoles().equals("institucion")){
                Optional<Enlace> listaDistribuidores = repoEnlaces.findByInstitucion(usuarioActual);
                Usuario distribuidor=listaDistribuidores.get().getDistribuidor();
                //System.out.println(distribuidor.getEmail());
                model.addAttribute("menus",repoMenus.encontrarPorUsuario(distribuidor.getEmail()));

            }else if (usuarioActual.getRoles().equals("distribuidor")){
                model.addAttribute("menus",repoMenus.encontrarPorUsuario(usuarioActual.getEmail()));
                return "agregarmenu";
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return "error";
    }

    @PostMapping("/nuevomenu")
    public String agregarMenu(
            @RequestParam(name = "nombre") String nombre,
            @RequestParam(name = "descripcion")String descripcion,
            @RequestParam(name = "icono") MultipartFile icono,
            Principal principal
    ) throws IOException {
            String iconoB64 = codificarImagen(icono);
            repoMenus.save(new Menu(nombre,descripcion,iconoB64,servicioUsuarios.obtener(principal.getName())));
            return "redirect:/vermenus";

    }



    //Utilidades
    public Usuario obtenerUsuarioActual(Principal principal){
        Usuario usuarioActual = servicioUsuarios.obtener(principal.getName());
        return usuarioActual;
    }

    /*
    public int obtenerIdDetalle(Principal principal){
        Usuario usuarioActual = servicioUsuarios.obtener(principal.getName());
        int id_detalle = repoDetallepedidos.encontrarPorUsuario(usuarioActual);
        return id_detalle;
    }
     */ //probar

    public String codificarImagen(MultipartFile icono) throws IOException {
        byte[] bytesIcono=icono.getBytes();
        String iconoB64 = Base64.getEncoder().encodeToString(bytesIcono);
        return iconoB64;
    }

}


