package br.senac.devweb.api.products.produto;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProdutoRepository extends PagingAndSortingRepository<Produto, Long>,
        QuerydslPredicateExecutor<Produto>,
        QuerydslBinderCustomizer<QProduto> {
    List<Produto> findAll(Predicate filter);

    default void customize(QuerydslBindings bindings, QProduto produto) {
        bindings.bind(produto.nome).first(StringExpression::containsIgnoreCase);
        bindings.bind(produto.descricao).first(StringExpression::containsIgnoreCase);
        bindings.bind(produto.complemento).first(StringExpression::containsIgnoreCase);
        bindings.bind(produto.fornecedor).first(StringExpression::containsIgnoreCase);
    }
}
