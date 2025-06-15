/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.kaue.FastFuriousFood.domain.service;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import local.kaue.FastFuriousFood.domain.model.Pedido;
import local.kaue.FastFuriousFood.domain.model.Produto;
import local.kaue.FastFuriousFood.domain.model.StatusPedido;
import local.kaue.FastFuriousFood.domain.repository.PedidoRepository;
import local.kaue.FastFuriousFood.domain.repository.ProdutoRepository;
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

    @Autowired
    private ProdutoRepository produtoRepository;

    public boolean existsById(Long id) {
        return pedidoRepository.existsById(id);
    }

    public List<Pedido> findAll() {
        return (List<Pedido>) pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido criar(Pedido pedido) {
        pedido.setDataAbertura(LocalDateTime.now());
        pedido.setStatus(StatusPedido.ABERTO);

        // Carrega os produtos completos pelo ID
        List<Produto> produtosCompletos = new ArrayList<>();
        for (Produto p : pedido.getProdutos()) {
            Produto produtoDoBanco = produtoRepository.findById(p.getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + p.getId()));
            produtosCompletos.add(produtoDoBanco);

        }
        pedido.setProdutos(produtosCompletos);
        
        calcularPrecoTotal(pedido);
        return pedidoRepository.save(pedido);
        
    }

     public Pedido atualizarPedido(Long pedidoId, Pedido pedidoAtualizado) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        if (pedidoExistente.getStatus() == StatusPedido.ENTREGUE) {
            throw new IllegalStateException("Pedido já foi entregue e não pode ser alterado.");
        }

        // Atualize os campos que quer permitir alterar
        pedidoExistente.setProdutos(pedidoAtualizado.getProdutos());
        pedidoExistente.setObservacao(pedidoAtualizado.getObservacao());
        pedidoExistente.setStatus(pedidoAtualizado.getStatus());
        // Atualize preço total recalculando, por exemplo
        calcularPrecoTotal(pedidoExistente);

        return pedidoRepository.save(pedidoExistente);
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

    public Pedido calcularPrecoTotal(Pedido pedido) {
        BigDecimal soma = BigDecimal.ZERO;
        if (pedido.getProdutos() != null) {
            for (Produto p : pedido.getProdutos()) {
                System.out.println("Produto: " + p.getNome() + ", preço: " + p.getPreco());
                soma = soma.add(p.getPreco());  // Sem null check, já que preco é primitivo
            }
        }
        System.out.println("Soma total: " + soma);
        pedido.setPreco_total(soma);
        return pedido;
    }

}
