package br.senac.devweb.api.products.categoria;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Long>,
        QuerydslPredicateExecutor<Categoria>,
        QuerydslBinderCustomizer<QCategoria> {

    List<Categoria> findAll(Predicate filter);

    default void customize(QuerydslBindings bindings, QCategoria categoria) {
        bindings.bind(categoria.descricao).first(StringExpression::containsIgnoreCase);
    }
}
