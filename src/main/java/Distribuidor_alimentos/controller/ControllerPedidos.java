package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.model.Usuario;
import Distribuidor_alimentos.repository.RepoPedidos;
import Distribuidor_alimentos.service.ServicioUsuarios;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;


@Controller
public class ControllerPedidos {
    @Autowired
    private RepoPedidos repoPedidos;
    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @GetMapping("/pedidos")
    public void listarPedidos(Model model, Principal principal) {
        Usuario usuarioActual = servicioUsuarios.obtener(principal.getName());
        model.addAttribute("pedidos",repoPedidos.encontrarPorUsuario(usuarioActual));
    }

    @GetMapping("/pedido/{id}")
    public Optional<Pedido> buscarPorID(@PathVariable int id) {
        return repoPedidos.findById(id);
    }

    @GetMapping("/nuevoPedido")
    public String nuevoPedido(){
        return "nuevoPedido";
    }


    @PostMapping("/hacerPedido")
    public String hacerPedido(
            @RequestParam(name = "tipo")String tipo,
            @RequestParam(name = "cantidad")int cantidad,
            @RequestParam(name = "nota")String nota,
            @RequestParam(name = "fecha")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Principal principal
    ){
        repoPedidos.save(new Pedido(tipo,cantidad,nota,fecha,servicioUsuarios.obtener(principal.getName())));
        return "redirect:confirmacion";
    }


    @PutMapping(value="/rectificarPedido")
    public String editarPedido(@PathVariable(value = "id") int id,
                                              @RequestParam(name = "tipo") String tipo,
                                              @RequestParam(name = "cantidad") int cantidad,
                                              @RequestParam(name = "nota") String nota,
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                              Model model
    ){
        model.addAttribute("pedido",repoPedidos.findById(id));
        Pedido pedidoAEditar=repoPedidos.findById(id).get();
        pedidoAEditar.setTipo(tipo);
        pedidoAEditar.setCantidad(cantidad);
        pedidoAEditar.setNota(nota);
        pedidoAEditar.setFecha(fecha);
        repoPedidos.save(pedidoAEditar);
        return "redirect:home";
    }

    @GetMapping("/borrarpedido/{id}")
    public String borrarPedido(@PathVariable(value = "id") int id) {
        if (repoPedidos.findById(id).isPresent()
                && !repoPedidos.findById(id).get().getFecha().isBefore(LocalDate.now())){
        repoPedidos.deleteById(id);}
        else{return "redirect:error";}
        return "redirect:confirmacion";
    }


    @GetMapping("/estadisticas/{id}")
    public String verEstadicticas(@PathVariable(value = "id") int id, Model model,Principal principal) {
        Usuario usuarioActual = servicioUsuarios.obtener(principal.getName());
        model.addAttribute("pedidoMasReciente",repoPedidos.findById(id));
        model.addAttribute("pedidoAnterior", repoPedidos.pedidoAnterior(usuarioActual));
        return "estadisticas";
    }



}


