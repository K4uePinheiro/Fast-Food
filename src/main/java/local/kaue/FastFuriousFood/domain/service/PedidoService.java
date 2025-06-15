/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.kaue.FastFuriousFood.domain.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import local.kaue.FastFuriousFood.domain.model.Pedido;
import local.kaue.FastFuriousFood.domain.model.Produto;
import local.kaue.FastFuriousFood.domain.model.StatusPedido;
import local.kaue.FastFuriousFood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaueg
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public boolean existsById(Long id) {
        return pedidoRepository.existsById(id);
    }

    public List<Pedido> listarPedidos() {
        return (List<Pedido>) pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido criar(Pedido pedido) {
        pedido.setDataAbertura(LocalDateTime.now());
        pedido.setStatus(StatusPedido.ABERTO); // opcional: definir status inicial
        return pedidoRepository.save(pedido);
    }

    public Pedido atualizarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void excluir(Long id) {
        pedidoRepository.deleteById(id);
    }

    public List<Pedido> listarPorStatus(StatusPedido status) {
        return (List<Pedido>) pedidoRepository.findByStatus(status);
    }

    public Pedido atualizarStatus(Long id, StatusPedido novoStatus) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);

        if (!pedidoOptional.isPresent()) {
            throw new RuntimeException("Pedido não encontrado");
        }

        Pedido pedido = pedidoOptional.get();
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }
   
}
