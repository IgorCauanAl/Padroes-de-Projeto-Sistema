package commerce.nobreza.loja.masculina.nobreza_loja.Service.facade.subsistema;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Subsistema Simulado 1: Serviço de Análise Antifraude
 * Avalia o risco da operação e aprova ou recusa pagamentos com base no comportamento do usuário.
 */
@Service
public class AntiFraudeService {

    public boolean analisarRiscoDeFraude(BigDecimal valor) {
        System.out.println("[AntiFraudeService] Analisando risco para a transação de R$ " + valor + "...");
        
        // Simulação básica: Rejeita transações estupidamente altas.
        if (valor.compareTo(new BigDecimal("50000")) > 0) {
            System.out.println("[AntiFraudeService] ALERTA: Risco detectado! Transação bloqueada por política antifraude.");
            return false;
        }

        System.out.println("[AntiFraudeService] Transação classificada como segura.");
        return true;
    }
}
