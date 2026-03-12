package commerce.nobreza.loja.masculina.nobreza_loja.Service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartaoPagamentoStrategy implements PagamentoStrategy {

    @Override
    public void processarPagamento(BigDecimal valor) {

        System.out.println("Processando pagamento via Cartão de Crédito no valor de: R$ " + valor);
       //ver dps se vai colocar o gateway de pagamento com algum banco
    }
}