package commerce.nobreza.loja.masculina.nobreza_loja.Service.facade.subsistema;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Implementação simulada da Processadora de Pagamento.
 * Em produção, esta classe faria uma chamada HTTP real à API da processadora (ex: Cielo, Stone).
 */
@Component
public class ProcessadoraPagamentoImpl implements ProcessadoraPagamento {

    @Override
    public boolean capturar(BigDecimal valor) {
        System.out.println("[ProcessadoraPagamento] Enviando captura de R$ " + valor + " para a processadora...");

        // Simulação: sempre captura com sucesso
        System.out.println("[ProcessadoraPagamento] Captura confirmada. Valor será repassado ao lojista.");
        return true;
    }
}
