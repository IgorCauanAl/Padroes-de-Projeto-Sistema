package commerce.nobreza.loja.masculina.nobreza_loja.Service.proxy;

import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Category;
import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Produto;
import commerce.nobreza.loja.masculina.nobreza_loja.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Primary
@Component
public class ProductCatalogProxy implements CatalogoServiceInterface{

    @Autowired
    private ProductService realService;

    @Autowired
    private CacheManager cacheManager;


    @Override
    public Page<Produto> buscarProdutoPorFiltro(Category category, Double min, Double max, Pageable pageable) {

            // cache para produtos
            Cache cache = cacheManager.getCache("produtos_filtrados");

            // gerar a chave única
            String key = (category != null ? category.getId() : "null") + "-" + min + "-" + max + "-" + pageable.getPageNumber();

            // buscar o valor no cache
            Cache.ValueWrapper wrapper = cache.get(key);

            if (wrapper != null) {
                System.out.println("LOG [PROXY]: Cache Hit (Sucesso) para a chave: " + key);
                return (Page<Produto>) wrapper.get();
            }

            // se não estiver no cache vamos para o service que contém a lógica
            System.out.println("Buscando no banco de dados...");
            Page<Produto> result = realService.buscarProdutoPorFiltro(category, min, max, pageable);

            // guardamos no cache com a sua chave
            cache.put(key, result);

            return result;
        }
    }

