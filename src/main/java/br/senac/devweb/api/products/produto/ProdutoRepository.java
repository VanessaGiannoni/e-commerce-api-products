package br.senac.devweb.api.products.produto;

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProdutoRepository extends PagingAndSortingRepository<Produto, Long>, QuerydslPredicateExecutor<Produto> {
    List<Produto> findAll(Predicate filter);
}
