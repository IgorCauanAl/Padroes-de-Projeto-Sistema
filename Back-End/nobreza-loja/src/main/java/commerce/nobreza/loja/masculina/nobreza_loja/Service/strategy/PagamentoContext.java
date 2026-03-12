package commerce.nobreza.loja.masculina.nobreza_loja.Service.strategy;

import commerce.nobreza.loja.masculina.nobreza_loja.Enum.TipoPagamento;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PagamentoContext {

    private final Map<String, PagamentoStrategy> estrategias;
    public PagamentoContext(Map<String, PagamentoStrategy> estrategias) {
        this.estrategias = estrategias;
    }

    public void realizarPagamento(TipoPagamento tipoPagamento, BigDecimal valor) {
        PagamentoStrategy strategy = getStrategy(tipoPagamento);
        if (strategy != null) {
            strategy.processarPagamento(valor);
        } else {
            throw new IllegalArgumentException("Tipo de pagamento não suportado: " + tipoPagamento);
        }
    }

    private PagamentoStrategy getStrategy(TipoPagamento tipoPagamento) {

        switch (tipoPagamento) {
            case PIX:
                return estrategias.get("pixPagamentoStrategy");
            case CARTAO_CREDITO:
                return estrategias.get("cartaoPagamentoStrategy");
            case BOLETO:
                return estrategias.get("boletoPagamentoStrategy");
            default:
                return null;
        }
    }
}