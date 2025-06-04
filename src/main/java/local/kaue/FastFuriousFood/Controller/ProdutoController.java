/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.kaue.FastFuriousFood.Controller;

import java.util.ArrayList;
import java.util.List;
import local.kaue.FastFuriousFood.domain.model.Produto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sesideva
 */
@RestController
public class ProdutoController {

    List<Produto> listaProduto;

    @GetMapping("/produto")
    public List<Produto> listas() {

        listaProduto = new ArrayList<Produto>();
        listaProduto.add(new Produto(1, "X-salada", 12.99, "Lanches"));
        listaProduto.add(new Produto(2, "X-frango", 13.99, "Lanches"));
        listaProduto.add(new Produto(3, "X-burguer", 9.99, "Lanches"));

        return listaProduto;

    }

}
