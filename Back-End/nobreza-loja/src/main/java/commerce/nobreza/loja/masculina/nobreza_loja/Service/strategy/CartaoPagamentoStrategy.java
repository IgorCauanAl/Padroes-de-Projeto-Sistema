package commerce.nobreza.loja.masculina.nobreza_loja.Service.strategy;

import commerce.nobreza.loja.masculina.nobreza_loja.Service.facade.PagamentoCartaoFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CartaoPagamentoStrategy implements PagamentoStrategy {

    private final PagamentoCartaoFacade pagamentoCartaoFacade;

    @Override
    public void processarPagamento(BigDecimal valor) {
        // Chamada única e simples para o "cliente", abstraindo a cadeia antifraude e de gateway.
        pagamentoCartaoFacade.finalizarPagamento(valor);
    }
}