package commerce.nobreza.loja.masculina.nobreza_loja.Service.observer;

import commerce.nobreza.loja.masculina.nobreza_loja.Entity.CarrinhoItens;
import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Pedido;
import commerce.nobreza.loja.masculina.nobreza_loja.Repository.CarrinhoItensRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Observer responsável por limpar os itens do carrinho do usuário 
 * logo após o pedido ter sido processado e salvo com sucesso.
 */
@Component
@RequiredArgsConstructor
public class LimpezaCarrinhoObserver implements PedidoObserver {

    private final CarrinhoItensRepository carrinhoItensRepository;

    @Override
    public void update(Pedido pedido) {
        // Encontra os itens no carrinho pertencentes ao usuário que fez a compra
        List<CarrinhoItens> itensCarrinho = carrinhoItensRepository.findByUsuario(pedido.getUsuario());
        
        // Remove os itens, esvaziando o carrinho
        if (!itensCarrinho.isEmpty()) {
            carrinhoItensRepository.deleteAll(itensCarrinho);
        }
    }
}
