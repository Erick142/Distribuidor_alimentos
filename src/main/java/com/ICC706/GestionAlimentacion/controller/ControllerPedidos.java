package com.ICC706.GestionAlimentacion.controller;
import com.ICC706.GestionAlimentacion.DAO.PedidoDAO;
import com.ICC706.GestionAlimentacion.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;


@Controller
public class ControllerPedidos {

    @Autowired
    private PedidoDAO pedidoDAO;

    @GetMapping("/nuevoPedido")
    public String nuevoPedido(){
        return "nuevoPedido";
    }

    //creacion de pedidos
    @PostMapping("/hacerPedido")
    public String hacerPedido(
            @RequestParam(name = "tipo")String tipo,
            @RequestParam(name = "cantidad")int cantidad,
            @RequestParam(name = "nota")String nota,
            //@RequestParam(name = "fecha") String fecha
            @DateTimeFormat(pattern = "DD-MM-YYYY") Date fecha
    ){
        pedidoDAO.save(new Pedido(tipo,cantidad,nota,fecha));
        return "redirect:success";
    }


    @RequestMapping(value="/rectificarPedido", method = RequestMethod.POST)
    public @ResponseBody Pedido editarUsuario(Pedido pedidoAEditar){
        pedidoAEditar.setTipo(pedidoAEditar.getTipo());
        pedidoAEditar.setCantidad(pedidoAEditar.getCantidad());
        pedidoAEditar.setFecha(pedidoAEditar.getFecha());
        pedidoAEditar.setNota(pedidoAEditar.getNota());
        pedidoDAO.save(pedidoAEditar);
        return pedidoAEditar;
    }


    /*
    @PostMapping
    public Pedido nuevoPedido(@RequestBody Pedido pedido) {
        return ControllerPedidos.guardarPedido(pedido);
    }

    private static Pedido guardarPedido(Pedido pedido) {
        return pedido;
    }

    @GetMapping
    public List<Pedido> consultarPedidos() {
        return pedidoDAO.findById();
    }

    @GetMapping("/pedido/{id}")
    public Pedido buscarPorID(@PathVariable long id) {
        return pedidoDAO.findById();
    }

    @PutMapping
    public Pedido editarPedido(@RequestBody Pedido pedido) {
        return pedidoDAO.save(pedido);
    }

    @DeleteMapping("{id}")
    public String borrarPedido(@PathVariable int id) {
        return pedidoDAO.delete();
    }

     */

}


