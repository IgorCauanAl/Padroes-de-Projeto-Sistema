package commerce.nobreza.loja.masculina.nobreza_loja.Service;

import commerce.nobreza.loja.masculina.nobreza_loja.DTO.CarrinhoItensDTO;
import commerce.nobreza.loja.masculina.nobreza_loja.DTO.CheckoutPageDataDTO;
import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Endereco;
import commerce.nobreza.loja.masculina.nobreza_loja.Entity.MetodoPagamento;
import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Pedido;
import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Usuario;
import commerce.nobreza.loja.masculina.nobreza_loja.Repository.EnderecoRepository;
import commerce.nobreza.loja.masculina.nobreza_loja.Repository.MetodoPagamentoRepository;
import commerce.nobreza.loja.masculina.nobreza_loja.Repository.PedidoRepository;
import commerce.nobreza.loja.masculina.nobreza_loja.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class CheckoutService {

    private final CarrinhoService carrinhoService;
    private final UserRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final PedidoRepository pedidoRepository;

    @Transactional(readOnly = true)
    public CheckoutPageDataDTO carregarDadosCheckout(String userEmail) {
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<CarrinhoItensDTO> itemsParaCheckout = carrinhoService.getItensDoUsuario(userEmail);
        BigDecimal precoTotal = itemsParaCheckout.stream()
                .map(item -> item.getPrecoProduto().multiply(new BigDecimal(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Endereco> enderecos = enderecoRepository.findByUsuario(usuario);
        List<MetodoPagamento> metodosPagamento = metodoPagamentoRepository.findByUsuario(usuario);

        return new CheckoutPageDataDTO(itemsParaCheckout, precoTotal, enderecos, metodosPagamento);
    }

    @Transactional(readOnly = true)
    public Pedido buscarPedidoDoUsuario(Long pedidoId, String userEmail) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (!pedido.getUsuario().getEmail().equals(userEmail)) {
            throw new SecurityException("Acesso negado ao pedido");
        }

        return pedido;
    }
}
