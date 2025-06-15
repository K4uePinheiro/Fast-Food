/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.kaue.FastFuriousFood.Controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import local.kaue.FastFuriousFood.DTO.AtualizarPedidoDTO;

import local.kaue.FastFuriousFood.DTO.StatusPedidoDTO;
import local.kaue.FastFuriousFood.domain.model.Pedido;
import local.kaue.FastFuriousFood.domain.model.Produto;
import local.kaue.FastFuriousFood.domain.model.StatusPedido;
import local.kaue.FastFuriousFood.domain.repository.PedidoRepository;
import local.kaue.FastFuriousFood.domain.repository.ProdutoRepository;
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
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    private ProdutoRepository produtoRepository;

    //mpstra todos
    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.findAll();
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

    //Status
    @GetMapping("/status/{status}")
    public List<Pedido> findByStatus(@PathVariable StatusPedido status) {
        List<Pedido> pedido = pedidoService.listarPorStatus(status);
        if (pedido.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não temos um pedido com esse status");
        }
        return pedido;
    }

    //vai criar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido criar(@RequestBody Pedido pedido) {
        return pedidoService.criar(pedido);
    }

    //Vai atualizar- mexer no caso
    @PutMapping("/{id}")
    public Pedido atualizarPedido(@PathVariable Long id, @RequestBody AtualizarPedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (dto.getProdutosIds() != null && !dto.getProdutosIds().isEmpty()) {
            List<Produto> produtos = produtoRepository.findAllById(dto.getProdutosIds());
            pedido.setProdutos(produtos);
        }

        if (dto.getObservacao() != null) {
            pedido.setObservacao(dto.getObservacao());
        }

        return pedidoRepository.save(pedido);
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

    @PutMapping("/status/{id}")
    public Pedido atualizarStatus(@PathVariable Long id, @RequestBody StatusPedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (dto.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'status' não pode ser nulo");
        }

        StatusPedido statusAtual = pedido.getStatus();

        if (statusAtual == StatusPedido.ENTREGUE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Não é permitido alterar o status se o pedido foi entregue");

        }

        StatusPedido novoStatus;
        try {

            novoStatus = StatusPedido.valueOf(dto.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido: " + dto.getStatus());
        }

        pedido.setStatus(novoStatus);

        if (novoStatus == StatusPedido.PRONTO || novoStatus == StatusPedido.CANCELADO) {
            pedido.setDataFinalizacao(LocalDateTime.now());
        } else {
            pedido.setDataFinalizacao(null);
        }

        return pedidoRepository.save(pedido);
    }
}
