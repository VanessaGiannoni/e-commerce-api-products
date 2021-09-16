package br.senac.devweb.api.products.categoria;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface CategoriaRepository extends CrudRepository<Categoria, Long>, QuerydslPredicateExecutor<Categoria> {

}
