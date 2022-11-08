package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.*;
import Distribuidor_alimentos.repository.RepoEnlaces;
import Distribuidor_alimentos.repository.RepoMenus;
import Distribuidor_alimentos.repository.RepoDetallepedidos;
import Distribuidor_alimentos.repository.RepoPedido;
import Distribuidor_alimentos.service.ServicioUsuarios;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RepoDetallepedidos repoDetallepedidos;
    @Autowired
    private ServicioUsuarios servicioUsuarios;
    @Autowired
    private RepoMenus repoMenus;
    @Autowired
    private RepoEnlaces repoEnlaces;
    @Autowired
    private RepoPedido repoPedido;

    int id;
    List<DetallePedido> listaDetallepedido = new ArrayList<>();
    private Pedido nuevoPedido = new Pedido();

    @GetMapping("/pedidos")
    public void listarPedidos(Model model, Principal principal) {
        Usuario usuarioActual = obtenerUsuarioActual(principal);
        model.addAttribute("pedidos", repoDetallepedidos.encontrarPorUsuario(usuarioActual));
    }

    @GetMapping("/pedido/{id}")
    public Optional<DetallePedido> buscarPorID(@PathVariable int id) {
        return repoDetallepedidos.findById(id);
    }


    @GetMapping("/nuevoPedido")
    public String nuevoPedido(Model model, Principal principal){
        listarMenus(model, principal);
        listarDetallePedido(model,principal,listaDetallepedido);

        model.addAttribute("pedidos", listaDetallepedido);
        return "nuevoPedido";
    }



    @PostMapping("/agregaralista")
    public String agregarAlista(
            @RequestParam(name = "tipo") String tipo,
            @RequestParam(name = "cantidad")int cantidad,
            @RequestParam(name = "nota")String nota,
            Model model,
            Principal principal
    ){
        listarDetallePedido(model,principal,listaDetallepedido);
        Usuario usuarioActual=obtenerUsuarioActual(principal);

        repoPedido.save(this.nuevoPedido);
        id=nuevoPedido.getId();
        //DetallePedido b = new DetallePedido();

        //listar menus
        listarMenus(model,principal);
        Pedido pedido = repoPedido.findById(id).get();
        DetallePedido detallepedidoAux = new DetallePedido(tipo,cantidad,nota,pedido,usuarioActual);
        listaDetallepedido.add(detallepedidoAux);
        System.out.println(detallepedidoAux);
        System.out.println(listaDetallepedido);
        //DetallePedido detalle = new DetallePedido(tipo,cantidad,nota,pedido);
        //repoDetallepedidos.save(new DetallePedido(tipo,cantidad,nota,fecha));
        //Pedido pedidoActual= repoPedido.findById(id).get();
        //repoPedido.save(pedidoActual);
        //repoDetallepedidos.save(detalle);

        //hacerPedido(model,principal,nuevoPedido);

        return "redirect:/nuevoPedido";
    }

    @DeleteMapping("/cancelarpedido")
    public String cancelarPedido(){
        borrarPedido(id);
    return "redirect:/home";
    }


    @GetMapping("/listardetalle")
    public String listarDetallePedido(Model model,Principal principal, List<DetallePedido> listaDetallepedido){
        Usuario usuarioActual = obtenerUsuarioActual(principal);
        //List lista_detalle =repoDetallepedidos.encontrarPorUsuario(usuarioActual);
        //System.out.println("institucion : "+usuarioActual);
        //System.out.println("lista : "+lista_detalle);
        model.addAttribute("detallepedidos",listaDetallepedido );
        return "redirect:/nuevoPedido";
    }

    @PostMapping("/hacerpedido")
    public String hacerPedido(
            @RequestParam(name = "fecha")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntrega,
            Model model,
            Principal principal
    ){
        listarMenus(model,principal);
        //,id_detallePedido
        Usuario usuarioActual = obtenerUsuarioActual(principal);
        //DetallePedido detallePedido = repoDetallepedidos.findById(id).get();
        //Pedido pedidoActual=repoPedido.findById(id).get();
        LocalDate fechaCreacion = LocalDate.now();
        nuevoPedido.setFechaCreacion(fechaCreacion);
        nuevoPedido.setUsuario(usuarioActual);
        nuevoPedido.setFechaEntrega(fechaEntrega);

        for(DetallePedido pedido:listaDetallepedido) {
            repoDetallepedidos.save(pedido);
        }
        repoPedido.save(nuevoPedido);
        return "redirect:/home";
    }

    @GetMapping("/rectificar/{id}")
    public String rectificar(@PathVariable(value = "id") int id_pedido, Model model, Principal principal) {
        Usuario usuarioActual = obtenerUsuarioActual(principal);
        model.addAttribute("pedidos", repoDetallepedidos.obtenerDetalle(id_pedido));
    return "rectificarPedido";}

    @GetMapping("/verrectificar")
    public String verrectificar() {
        return "rectificarPedido";}

    @PutMapping(value="/editarPedido/{id}")
    public String editarPedido(@PathVariable(value = "id") int id_pedido,
                                              @RequestParam(name = "tipo") String tipo,
                                              @RequestParam(name = "cantidad") int cantidad,
                                              @RequestParam(name = "nota") String nota,
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                              Model model
    ){
        model.addAttribute("pedido", repoDetallepedidos.findById(id_pedido));
        DetallePedido detallepedidoAEditar = repoDetallepedidos.findById(id_pedido).get();
        detallepedidoAEditar.setTipo(tipo);
        detallepedidoAEditar.setCantidad(cantidad);
        detallepedidoAEditar.setNota(nota);
        repoDetallepedidos.save(detallepedidoAEditar);
        return "redirect:home";
    }

    //Eliminar pedido
    @GetMapping("/borrarpedido/{id}")
    public String borrarPedido(@PathVariable(value = "id") int id_pedido) {
        if (repoPedido.findById(id_pedido).isPresent()
                && !repoPedido.findById(id_pedido).get().getFechaEntrega().isBefore(LocalDate.now())){
        repoPedido.deleteById(id_pedido);}
        else{return "redirect:error";}
        return "redirect:confirmacion";
    }


    @GetMapping("/verestadisticas")
    public String estadisticas(Model model,Principal principal) {

        List<Pedido> pedidoMasReciente = repoPedido.pedidoActual(obtenerUsuarioActual(principal));
        List<Pedido> pedidoAnterior = repoPedido.pedidoAnterior(obtenerUsuarioActual(principal));
        int indRec = pedidoMasReciente.indexOf(id);
        int indAnt = pedidoAnterior.indexOf(id);
        int idRec = pedidoMasReciente.get(0).getId();
        System.out.println("id 1 :"+idRec);
        int idAnt = pedidoAnterior.get(0).getId();
        System.out.println("id 2 :"+idAnt);
        model.addAttribute("pedidoMasReciente", repoDetallepedidos.encontrarPorUsuario(obtenerUsuarioActual(principal)));
        model.addAttribute("pedidoAnterior", repoDetallepedidos.findById(idAnt).get());
        System.out.println("pedido mas reciente :" + repoDetallepedidos.findById(idRec).get());
        System.out.println("pedido anterior :"+ repoDetallepedidos.findById(idAnt).get());


        return "estadisticas";
    }

    //Estadísticas
    @GetMapping("/estadisticas/{id}")
    public String verEstadisticas(@PathVariable(value = "id") int id_pedido, Model model,Principal principal) {
        int idPedidoActual = repoPedido.idActual(obtenerUsuarioActual(principal));
        int idPedidoAnterior = repoPedido.idAnterior(obtenerUsuarioActual(principal));
        model.addAttribute("pedidoMasReciente", repoDetallepedidos.obtenerDetalleActual(idPedidoActual));
        model.addAttribute("pedidoAnterior", repoDetallepedidos.obtenerDetalleAnterior(idPedidoAnterior));
        Usuario usuarioActual=obtenerUsuarioActual(principal);
        Optional<Enlace> listaDistribuidores = repoEnlaces.findByInstitucion(usuarioActual);
        Usuario distribuidor=listaDistribuidores.get().getDistribuidor();
        LocalDate fecha = repoPedido.pedidoActual(usuarioActual).get(0).getFechaCreacion();
        model.addAttribute("nombreDist", distribuidor);
        model.addAttribute("nombreUsuario",usuarioActual);
        model.addAttribute("fechaSolicitud",fecha);
        model.addAttribute("idPedido",idPedidoActual);

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

    public String codificarImagen(MultipartFile icono) throws IOException {
        byte[] bytesIcono=icono.getBytes();
        String iconoB64 = Base64.getEncoder().encodeToString(bytesIcono);
        return iconoB64;
    }

}


