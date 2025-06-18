/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.kaue.FastFuriousFood.DTO;

import java.util.List;
import local.kaue.FastFuriousFood.domain.model.StatusPedido;

/**
 *
 * @author kaueg
 */
public class AtualizarPedidoDTO {

    private List<Long> produtosIds;
    private String observacao;

    public List<Long> getProdutosIds() {
        return produtosIds;
    }

    public void setProdutosIds(List<Long> produtosIds) {
        this.produtosIds = produtosIds;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
