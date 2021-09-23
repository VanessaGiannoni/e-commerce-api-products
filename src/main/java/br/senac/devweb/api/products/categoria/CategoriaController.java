package br.senac.devweb.api.products.categoria;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categoria")
@AllArgsConstructor
public class CategoriaController {

    private CategoriaService categoriaService;

    @PostMapping("/")
    public ResponseEntity<CategoriaRepresentation.Detail> createCategoria(
           @Valid @RequestBody CategoriaRepresentation.CreateCategoria createCategoria) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoriaRepresentation.Detail.from(this.categoriaService.save(createCategoria)));
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoriaRepresentation.Detail> updateCategoria(@PathVariable("id") Long id,
           @Valid @RequestBody CategoriaRepresentation.CreateCategoria createCategoria ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                CategoriaRepresentation.Detail.from(this.categoriaService.update(id, createCategoria))
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoriaRepresentation.Detail> readCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(CategoriaRepresentation.Detail.from(this.categoriaService.getCategory(id)));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<CategoriaRepresentation.Lista>> readCategorias() {
        BooleanExpression filter = QCategoria.categoria.status.eq(Categoria.Status.ATIVO);

        return ResponseEntity.ok(CategoriaRepresentation.Lista
                        .from(this.categoriaService.getAllCategories(filter)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCategoria(@PathVariable("id") Long id) {
        this.categoriaService.deleteCategoria(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
