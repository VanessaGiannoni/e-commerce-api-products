package br.senac.devweb.api.products.util;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class Pagination {
    private Integer pageSize;
    private Integer selectedPage;
    private Integer pageCount;
    private List<?> content;

}
