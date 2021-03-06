package br.senac.devweb.api.products.produto;

import br.senac.devweb.api.products.categoria.Categoria;
import br.senac.devweb.api.products.categoria.CategoriaService;
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
@RequestMapping("/produto")
@AllArgsConstructor
public class ProdutoController {

    private ProdutoService produtoService;
    private ProdutoRepository produtoRepository;

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

    @GetMapping("/")
    public ResponseEntity<Pagination> readProdutos(
            @QuerydslPredicate(root = Produto.class) Predicate filters,
            @Valid @RequestParam(name = "selectedPage", required = false, defaultValue = "1") Integer selectedPage,
            @RequestParam(name = "pageSizer", required = false, defaultValue = "20") Integer pageSize

    ) {
        BooleanExpression filter = Objects.isNull(filters) ?
                QProduto.produto.status.eq(Produto.Status.ATIVO) :
                QProduto.produto.status.eq(Produto.Status.ATIVO).and(filters);

        if (selectedPage <= 0) {
            throw new IllegalArgumentException("O n??mero da p??gina n??o pode ser 0 ou menor que 0");
        }

        Pageable pageRequest = PageRequest.of(selectedPage-1, pageSize);

        Page<Produto> produtoPage = this.produtoRepository.findAll(filter, pageRequest);

        Pagination pagination = Pagination
                .builder()
                .content(
                        ProdutoRepresentation.Lista.from(produtoPage.getContent())
                )
                .selectedPage(selectedPage)
                .pageSize(pageSize)
                .pageCount(produtoPage.getTotalPages())
                .build();

        return ResponseEntity.ok(pagination);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdutoRepresentation.Detail> deleteProduto(@PathVariable("id") Long id) {
        this.produtoService.deleteProduto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
