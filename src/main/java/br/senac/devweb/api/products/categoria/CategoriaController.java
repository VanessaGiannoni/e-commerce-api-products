package br.senac.devweb.api.products.categoria;

import br.senac.devweb.api.products.util.Pagination;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/categoria")
@AllArgsConstructor
public class CategoriaController {

    private CategoriaService categoriaService;
    private CategoriaRepository categoriaRepository;

    @PostMapping("/")
    public ResponseEntity<CategoriaRepresentation.Detail> createCategoria(
           @Valid @RequestBody CategoriaRepresentation.CreateCategoria createCategoria) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoriaRepresentation.Detail.from(this.categoriaService.save(createCategoria)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaRepresentation.Detail> updateCategoria(@PathVariable("id") Long id,
           @Valid @RequestBody CategoriaRepresentation.CreateCategoria createCategoria ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                CategoriaRepresentation.Detail.from(this.categoriaService.update(id, createCategoria))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaRepresentation.Detail> readCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(CategoriaRepresentation.Detail.from(this.categoriaService.getCategory(id)));
    }

    @GetMapping("/")
    public ResponseEntity<Pagination> readCategorias(
            @QuerydslPredicate(root = Categoria.class) Predicate filters,
            @Valid @RequestParam(name = "selectedPage", required = false, defaultValue = "1") Integer selectedPage,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize
    ) {
        BooleanExpression filter = Objects.isNull(filters) ?
                QCategoria.categoria.status.eq(Categoria.Status.ATIVO) :
                QCategoria.categoria.status.eq(Categoria.Status.ATIVO).and(filters);

        if (selectedPage <= 0) {
            throw new IllegalArgumentException("O número da página não pode ser 0 ou menor que 0");
        }

        Pageable pageRequest = PageRequest.of(selectedPage-1, pageSize);

        Page<Categoria> categoriaPage = this.categoriaRepository.findAll(filter, pageRequest);

        Pagination pagination = Pagination
                .builder()
                .content(
                        CategoriaRepresentation.Lista.from(categoriaPage.getContent())
                )
                .selectedPage(selectedPage)
                .pageSize(pageSize)
                .pageCount(categoriaPage.getTotalPages())
                .build();

        return ResponseEntity.ok(pagination);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCategoria(@PathVariable("id") Long id) {
        this.categoriaService.deleteCategoria(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
