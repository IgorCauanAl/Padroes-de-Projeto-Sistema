package commerce.nobreza.loja.masculina.nobreza_loja.Service.strategy;

import java.math.BigDecimal;

public interface PagamentoStrategy {
    void processarPagamento(BigDecimal valor);
}