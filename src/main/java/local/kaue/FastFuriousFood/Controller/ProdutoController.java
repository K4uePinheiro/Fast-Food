/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.kaue.FastFuriousFood.Controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import local.kaue.FastFuriousFood.domain.model.Produto;
import local.kaue.FastFuriousFood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sesideva
 */
@RestController
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/produto")
    public List<Produto> listas() {

        //return produtoRepository.findAll();
        return produtoRepository.findByNome("X-Salada");

    }

    @GetMapping("/produto/{produtoID}")
    public ResponseEntity<Produto> buscar(@PathVariable Long produtoID) {

        Optional<Produto> produto = produtoRepository.findById(produtoID);

        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();

        }

    }
    
    @PostMapping("/produto")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@RequestBody Produto produto){
        
        return produtoRepository.save(produto);
    }

    
    @PutMapping("/produto/{produtoID}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long produtoID,
                                             @RequestBody Produto produto){
        //Verificase o cliente  existe
        if (!produtoRepository.existsById(produtoID)){
            return ResponseEntity.notFound().build();
        }
        produto.setId(produtoID);
        produto = produtoRepository.save(produto);
        return ResponseEntity.ok(produto);
    }
    
    
    @DeleteMapping("/produto/{produtoID}")
    public ResponseEntity<Void> excluir(@PathVariable Long produtoID){
        //verifica se produto existe ou não
        
        if(!produtoRepository.existsById(produtoID)){
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(produtoID);
        return ResponseEntity.noContent().build();
    }
}
