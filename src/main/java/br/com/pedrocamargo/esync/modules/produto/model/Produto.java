package br.com.pedrocamargo.esync.modules.produto.model;

import br.com.pedrocamargo.esync.modules.comprador.model.Comprador;
import br.com.pedrocamargo.esync.modules.fornecedor.model.Fornecedor;
import br.com.pedrocamargo.esync.modules.locacao.model.Locacao;
import br.com.pedrocamargo.esync.modules.produto.dto.ProdutoDTORequest;
import br.com.pedrocamargo.esync.modules.tipoproduto.model.TipoProduto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "produto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Double preco;
    private LocalDate datacompra;
    private Boolean is_active;
    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;
    @ManyToOne
    @JoinColumn(name = "id_comprador")
    private Comprador comprador;
    @ManyToOne
    @JoinColumn(name = "id_tipoproduto")
    private TipoProduto tipoProduto;

    @OneToOne
    @JoinColumn(name = "id_produto")
    private Locacao locacao;

    public Produto(Fornecedor fornecedor, Comprador comprador, TipoProduto tipoProduto, ProdutoDTORequest produtoRequest) {
        this.descricao = produtoRequest.descricao();
        this.preco = produtoRequest.preco();
        this.datacompra = produtoRequest.datacompra();
        this.is_active = true;
        this.fornecedor = fornecedor;
        this.comprador = comprador;
        this.tipoProduto = tipoProduto;
    }

    public void update(Fornecedor fornecedor, Comprador comprador, TipoProduto tipoProduto, ProdutoDTORequest produtoDTORequest) {
        this.fornecedor = fornecedor;
        this.comprador = comprador;
        this.tipoProduto = tipoProduto;
        Map<String,Object> mapAttributes = produtoDTORequest.mapAttributes();
        for(String keyAttribute : mapAttributes.keySet()){
            if(mapAttributes.get(keyAttribute) != null){
                this.setAttributeByNameString(keyAttribute, mapAttributes.get(keyAttribute));
            }
        }
    }

    private void setAttributeByNameString(String keyAttribute, Object o) {
        switch (keyAttribute){
            case "descricao":
                this.descricao = (String) o;
                break;
            case "preco":
                this.preco = (Double) o;
                break;
            case "datacompra":
                this.datacompra = (LocalDate) o;
                break;
        }
    }

    public void delete() {
        this.is_active = false;
    }
}
