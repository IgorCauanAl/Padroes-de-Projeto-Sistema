package commerce.nobreza.loja.masculina.nobreza_loja.Service.facade.subsistema;

import java.math.BigDecimal;

/**
 * Interface do Subsistema Externo 1: Autorizador Bancário.
 * Representa a comunicação com o banco emissor do cartão,
 * responsável por autorizar ou recusar a transação.
 */
public interface AutorizadorBancario {
    boolean autorizar(String numeroCartao, BigDecimal valor);
}
