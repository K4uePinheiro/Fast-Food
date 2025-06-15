/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.kaue.FastFuriousFood.domain.service;

import java.util.List;
import java.util.Optional;
import local.kaue.FastFuriousFood.domain.model.Produto;
import local.kaue.FastFuriousFood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kaueg
 */
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public boolean existsById(Long id) {
        return produtoRepository.existsById(id);
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> listarProdutos() {
        return (List<Produto>) produtoRepository.findAll();
    }

    public List<Produto> listByCategoria(String categoria) {
        return produtoRepository.findByCategoriaContainingIgnoreCase(categoria);
    }

    public Produto salvar(Produto produto) {
        // Verifica se já existe um produto com o mesmo nome
        List<Produto> produtoExistente = produtoRepository.findByNomeIgnoreCase(produto.getNome());

        if (!produtoExistente.isEmpty()) {
            throw new IllegalArgumentException("Produto já cadastrado com este nome: " + produto.getNome());
        }

        return produtoRepository.save(produto);
    }

    public void excluir(Long id) {
        produtoRepository.deleteById(id);
    }

}
