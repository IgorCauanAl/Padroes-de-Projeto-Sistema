package commerce.nobreza.loja.masculina.nobreza_loja.Service.proxy;

import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Category;
import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CatalogoServiceInterface {
    Page<Produto> buscarProdutoPorFiltro(Category category, Double min, Double max, Pageable pageable);
}
