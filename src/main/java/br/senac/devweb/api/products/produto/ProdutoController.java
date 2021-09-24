package br.senac.devweb.api.products.produto;

import br.senac.devweb.api.products.categoria.Categoria;
import br.senac.devweb.api.products.categoria.CategoriaService;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {

    private ProdutoService produtoService;

    private final CategoriaService categoriaService;

    @PostMapping("/")
    public ResponseEntity<ProdutoRepresentation.Detail> createProduto(
            @Valid @RequestBody ProdutoRepresentation.RepresentationProduto representationProduto) {

        Categoria categoria = this.categoriaService.getCategory(representationProduto.getCategoria());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ProdutoRepresentation.Detail.from(this.produtoService.save(representationProduto, categoria))
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detail> updateProduto(@PathVariable("id") Long id,
            @Valid @RequestBody ProdutoRepresentation.RepresentationProduto representationProduto) {

        Categoria categoria = this.categoriaService.getCategory(representationProduto.getCategoria());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ProdutoRepresentation.Detail.from(this.produtoService.update(id, representationProduto, categoria))
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detail> readProdutoById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ProdutoRepresentation.Detail.from(this.produtoService.getProduto(id)));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ProdutoRepresentation.Lista>> readProdutos() {
        BooleanExpression filter = QProduto.produto.status.eq(Produto.Status.ATIVO);

        return ResponseEntity.ok(ProdutoRepresentation.Lista
                .from(this.produtoService.getAllProdutos(filter))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detail> deleteProduto(@PathVariable("id") Long id) {
        this.produtoService.deleteProduto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
