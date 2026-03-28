package commerce.nobreza.loja.masculina.nobreza_loja.Service.observer;

import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Pedido;

/**
 * Interface base para o padrão Observer no contexto de finalização de Pedidos.
 * Qualquer serviço que necessite executar ações após um pedido ser criado e pago
 * deve implementar esta interface.
 */
public interface PedidoObserver {
    
    /**
     * Atualiza o observer com os detalhes do pedido recém concluído.
     * @param pedido O pedido criado e pago.
     */
    void update(Pedido pedido);
}
