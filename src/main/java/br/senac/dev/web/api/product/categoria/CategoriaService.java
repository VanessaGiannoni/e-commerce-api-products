package br.senac.dev.web.api.product.categoria;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

    public Categoria salvar(CategoriaRepresentation.CreateCategoria createCategoria) {
        return this.categoriaRepository.save(Categoria.builder()
                        .descricao(createCategoria.getDescricao())
                        .status(Categoria.Status.ATIVO)
                        .build());
    }
}
