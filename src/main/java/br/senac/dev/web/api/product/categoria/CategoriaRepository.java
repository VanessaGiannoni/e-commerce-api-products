package br.senac.dev.web.api.product.categoria;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoriaRepository extends CrudRepository<Categoria, UUID>,
        QuerydslPredicateExecutor<Categoria> {
}
