package commerce.nobreza.loja.masculina.nobreza_loja.Service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PixPagamentoStrategy implements PagamentoStrategy {

    @Override
    public void processarPagamento(BigDecimal valor) {

        System.out.println("Processando pagamento via PIX no valor de: R$ " + valor);
        //ver dps se vai colocar o gateway de pagamento com algum banco
    }
}