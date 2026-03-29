package commerce.nobreza.loja.masculina.nobreza_loja.Service.facade.subsistema;

import java.math.BigDecimal;

/**
 * Interface do Subsistema Externo 2: Processadora de Pagamento.
 * Representa a comunicação com a processadora (ex: Cielo, Stone),
 * responsável por capturar o valor autorizado e transferir para o lojista.
 */
public interface ProcessadoraPagamento {
    boolean capturar(BigDecimal valor);
}
