package commerce.nobreza.loja.masculina.nobreza_loja.Service.facade.subsistema;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Subsistema Simulado 2: Serviço de Gateway de Pagamento (Comunicação com a API de Bancos)
 * Responsável por repassar os dados do cartão ao adquirente.
 */
@Service
public class GatewayOperadoraService {

    public boolean capturarValorDaConta(BigDecimal valor) {
        System.out.println("[GatewayOperadoraService] Conectando com a API da operadora de crédito...");
        System.out.println("[GatewayOperadoraService] Capturando do saldo o valor de: R$ " + valor);
        System.out.println("[GatewayOperadoraService] Operação aceita pela operadora. Recibo gerado com sucesso.");
        
        return true; // Simula uma transação bem-sucedida do cartão
    }
}
