package commerce.nobreza.loja.masculina.nobreza_loja.Service.facade;

import commerce.nobreza.loja.masculina.nobreza_loja.Service.facade.subsistema.AutorizadorBancario;
import commerce.nobreza.loja.masculina.nobreza_loja.Service.facade.subsistema.ProcessadoraPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Padrão Facade (GoF):
 * Fornece uma interface unificada e simplificada para o conjunto de subsistemas
 * bancários externos necessários para processar um pagamento com cartão de crédito.
 *
 * O cliente (CartaoPagamentoStrategy) não precisa conhecer os detalhes de
 * autorização bancária nem de captura pela processadora — apenas chama o Facade.
 *
 * Os subsistemas são referenciados por interfaces (AutorizadorBancario, ProcessadoraPagamento),
 * seguindo o princípio de programar para interfaces do GoF.
 */
@Service
@RequiredArgsConstructor
public class PagamentoCartaoFacade {

    private final AutorizadorBancario autorizadorBancario;
    private final ProcessadoraPagamento processadoraPagamento;

    /**
     * Método único exposto ao cliente.
     * Internamente, orquestra a comunicação com os sistemas bancários externos:
     * 1. Solicita autorização ao banco emissor do cartão.
     * 2. Solicita a captura do valor à processadora.
     */
    public void processarPagamentoCartao(String numeroCartao, BigDecimal valor) {
        System.out.println("\n--- [Facade] Iniciando pagamento via Cartão de Crédito ---");

        // Etapa 1: Autorização junto ao banco emissor
        boolean autorizado = autorizadorBancario.autorizar(numeroCartao, valor);
        if (!autorizado) {
            throw new RuntimeException("Pagamento recusado: o banco emissor não autorizou a transação.");
        }

        // Etapa 2: Captura do valor pela processadora
        boolean capturado = processadoraPagamento.capturar(valor);
        if (!capturado) {
            throw new RuntimeException("Pagamento recusado: a processadora não conseguiu capturar o valor.");
        }

        System.out.println("--- [Facade] Pagamento via Cartão de Crédito concluído com sucesso! ---\n");
    }
}
