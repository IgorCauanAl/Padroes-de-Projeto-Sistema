package commerce.nobreza.loja.masculina.nobreza_loja.Service.facade;

import commerce.nobreza.loja.masculina.nobreza_loja.Service.facade.subsistema.AntiFraudeService;
import commerce.nobreza.loja.masculina.nobreza_loja.Service.facade.subsistema.GatewayOperadoraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Padrão Facade (Fachada):
 * Esconde a complexidade dos múltiplos serviços/subsistemas necessários 
 * para efetuar um pagamento em cartão e provê uma única interface simplificada 
 * para as camadas superiores (cliente/strategy) interagirem.
 */
@Service
@RequiredArgsConstructor
public class PagamentoCartaoFacade {

    private final AntiFraudeService antiFraudeService;
    private final GatewayOperadoraService gatewayOperadoraService;

    public void finalizarPagamento(BigDecimal valor) {
        System.out.println("\n--- [Facade] Iniciando processo complexo de Pagamento via Cartão ---");

        // Regra 1: Análise de Risco (Fraud check)
        boolean seguro = antiFraudeService.analisarRiscoDeFraude(valor);
        
        if (!seguro) {
            throw new RuntimeException("Falha no pagamento de cartão: Bloqueado por medida de segurança na análise de risco.");
        }

        // Regra 2: Captura com a Operadora Bancária
        boolean capturado = gatewayOperadoraService.capturarValorDaConta(valor);
        
        if (!capturado) {
            throw new RuntimeException("Falha no pagamento de cartão: A operadora recusou o cartão informado.");
        }

        System.out.println("--- [Facade] Pagamento de Cartão de Crédito Concluído com Sucesso ---");
    }
}
