package commerce.nobreza.loja.masculina.nobreza_loja.Service.strategy;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("boletoPagamentoStrategy")
public class BoletoPagamentoStrategy implements PagamentoStrategy {
    @Override
    public void processarPagamento(BigDecimal valor) {
        System.out.println("Pagamento de R$ " + valor + " via Boleto processado com sucesso!");
    }
}