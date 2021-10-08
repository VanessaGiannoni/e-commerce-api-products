package br.senac.devweb.api.products.produto;

import br.senac.devweb.api.products.categoria.Categoria;
import br.senac.devweb.api.products.exceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public Produto save(ProdutoRepresentation.RepresentationProduto repProduto, Categoria categoria) {

        return this.produtoRepository.save(
                Produto
                    .builder()
                    .nome(repProduto.getNome())
                    .descricao(repProduto.getDescricao())
                    .complemento(Strings.isEmpty(repProduto.getComplemento()) ? "" : repProduto.getComplemento())
                    .fabricante(repProduto.getFabricante())
                    .fornecedor(Strings.isEmpty(repProduto.getFornecedor()) ? "" : repProduto.getFornecedor())
                    .qtde(repProduto.getQtde())
                    .valor(repProduto.getValor())
                    .unidadeMedida(repProduto.getUnidadeMedida())
                    .categoria(categoria)
                    .status(Produto.Status.ATIVO)
                    .build()
        );
    }

    public Produto getProduto(Long id) {
        BooleanExpression filter = QProduto.produto.id.eq(id)
                .and(QProduto.produto.status.eq(Produto.Status.ATIVO));

        return this.produtoRepository.findOne(filter)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado!"));
    }

    public List<Produto> getAllProdutos(Predicate filter) {
        return this.produtoRepository.findAll(filter);
    }

    public Produto update(Long id, ProdutoRepresentation.RepresentationProduto repProduto, Categoria categoria) {
        Produto antigo = this.getProduto(id);


        Produto atualizado = antigo
                .toBuilder()
                .nome(repProduto.getNome())
                .descricao(repProduto.getDescricao())
                .complemento(Strings.isEmpty(repProduto.getComplemento()) ? "" : repProduto.getComplemento())
                .fabricante(repProduto.getFabricante())
                .fornecedor(Strings.isEmpty(repProduto.getFornecedor()) ? "" : repProduto.getFornecedor())
                .qtde(repProduto.getQtde())
                .valor(repProduto.getValor())
                .unidadeMedida(repProduto.getUnidadeMedida())
                .categoria(categoria)
                .build();

        return this.produtoRepository.save(atualizado);
    };

    public void deleteProduto(Long id) {
        Produto produto = this.getProduto(id);

        produto.setStatus(Produto.Status.INATIVO);

        this.produtoRepository.save(produto);
    }
}

