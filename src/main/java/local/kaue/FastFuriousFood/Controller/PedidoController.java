/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.kaue.FastFuriousFood.Controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import local.kaue.FastFuriousFood.DTO.StatusPedidoDTO;
import local.kaue.FastFuriousFood.domain.model.Pedido;
import local.kaue.FastFuriousFood.domain.model.Produto;
import local.kaue.FastFuriousFood.domain.model.StatusPedido;
import local.kaue.FastFuriousFood.domain.repository.PedidoRepository;
import local.kaue.FastFuriousFood.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kaueg
 */
@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    //mpstra todos
      @GetMapping
    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }
    
    //vai mostrar
    @GetMapping("{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Long id) {

        if (pedidoService.existsById(id)) {
            return ResponseEntity.ok(pedidoService.findById(id).get());
        } else {
            return ResponseEntity.notFound().build();

        }

    }

    //vai criar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido criar(@RequestBody Pedido pedido) {
        return pedidoService.criar(pedido);

    }

    //Vai atualizar- mexer no caso
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@Valid @PathVariable Long id,
            @RequestBody Pedido pedido) {
        //Verificase o cliente  existe
         if (pedidoService.existsById(id)) {
            pedido.setId(id);
            pedido = pedidoService.atualizarPedido(pedido);
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    //vai excluir
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        //verifica se produto existe ou não

        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.excluir(id);
        return ResponseEntity.noContent().build();

    }
    //altera o status

    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable Long id, @RequestBody StatusPedidoDTO statusDTO) {
        Pedido pedidoAtualizado = pedidoService.atualizarStatus(id, statusDTO.getStatus());
        return ResponseEntity.ok(pedidoAtualizado);
    }
}
