package commerce.nobreza.loja.masculina.nobreza_loja.Service.facade.subsistema;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Implementação simulada do Autorizador Bancário.
 * Em produção, esta classe faria uma chamada HTTP real à API do banco emissor.
 */
@Component
public class AutorizadorBancarioImpl implements AutorizadorBancario {

    @Override
    public boolean autorizar(String numeroCartao, BigDecimal valor) {
        System.out.println("[AutorizadorBancario] Conectando ao banco emissor do cartão...");
        System.out.println("[AutorizadorBancario] Solicitando autorização para R$ " + valor);

        // Simulação: sempre autoriza
        System.out.println("[AutorizadorBancario] Transação AUTORIZADA pelo banco emissor.");
        return true;
    }
}
