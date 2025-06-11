/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package local.kaue.FastFuriousFood.domain.repository;

import java.util.List;
import local.kaue.FastFuriousFood.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sesideva
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    
    
    List<Produto> findByNome(String nome);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    
    
}
