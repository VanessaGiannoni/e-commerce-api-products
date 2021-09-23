package br.senac.devweb.api.products.categoria;

import br.senac.devweb.api.products.exceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public Categoria save(CategoriaRepresentation.CreateCategoria createCategoria) {
        return this.categoriaRepository.save(
                Categoria
                        .builder()
                        .descricao(createCategoria.getDescricao())
                        .status(Categoria.Status.ATIVO)
                        .build()
        );
    }

    public List<Categoria> getAllCategories(Predicate filter) {
        return this.categoriaRepository.findAll(filter);
    }

    public void deleteCategoria(Long id) {
        Categoria categoria = this.getCategory(id);
        categoria.setStatus(Categoria.Status.INATIVO);
        this.categoriaRepository.save(categoria);
    }

    public Categoria getCategory(Long id) {
        BooleanExpression filter =
                QCategoria.categoria.id.eq(id)
                        .and(QCategoria.categoria.status.eq(Categoria.Status.ATIVO));

        return this.categoriaRepository.findOne(filter).orElseThrow(() -> new NotFoundException("Categoria não encontrada."));
    }

    public Categoria update(Long id, CategoriaRepresentation.CreateCategoria createOrUpdateCategoria) {
        Categoria categoria = getCategory(id);
        categoria.setDescricao(createOrUpdateCategoria.getDescricao());

        return this.categoriaRepository.save(categoria);
    }
}
