package commerce.nobreza.loja.masculina.nobreza_loja.Service.observer;

import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Pedido;
import commerce.nobreza.loja.masculina.nobreza_loja.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Observer responsável por notificar o usuário via e-mail quando um pedido é confirmado com sucesso.
 */
@Component
@RequiredArgsConstructor
public class EmailConfirmacaoObserver implements PedidoObserver {

    private final EmailService emailService;

    @Override
    public void update(Pedido pedido) {
        String destinatario = pedido.getUsuario().getEmail();
        String assunto = "Nobreza - Pedido #" + pedido.getId() + " Confirmado!";
        
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
        String valorFormatado = formatoMoeda.format(pedido.getValorTotal());

        String corpo = """
            Olá %s,

            Seu pedido de número #%d foi recebido e seu pagamento registrado com sucesso!
            Valor total: %s

            Acompanhe o status do seu pedido pelo painel do usuário.

            Atenciosamente,
            Equipe Nobreza Moda Masculina""".formatted(
            pedido.getUsuario().getNome(),
            pedido.getId(),
            valorFormatado
        );

        emailService.enviarEmail(destinatario, assunto, corpo);
    }
}
