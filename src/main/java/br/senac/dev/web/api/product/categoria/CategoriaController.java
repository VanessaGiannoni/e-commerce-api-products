package br.senac.dev.web.api.product.categoria;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categoria")
@AllArgsConstructor
public class CategoriaController {

    private CategoriaService categoriaService;

    @PostMapping
    @RequestMapping("/")
    public ResponseEntity<CategoriaRepresentation.Detail> createCategoria(
            @RequestBody CategoriaRepresentation.CreateCategoria createCategoria) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CategoriaRepresentation.Detail.from(this.categoriaService.salvar(createCategoria)));
    }
}
