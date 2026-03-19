package commerce.nobreza.loja.masculina.nobreza_loja.DTO;

import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Endereco;
import commerce.nobreza.loja.masculina.nobreza_loja.Entity.MetodoPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class CheckoutPageDataDTO {

    private List<CarrinhoItensDTO> checkoutItems;
    private BigDecimal totalPrice;
    private List<Endereco> enderecos;
    private List<MetodoPagamento> metodosPagamento;
}
