package commerce.nobreza.loja.masculina.nobreza_loja.Controller.Login;

import commerce.nobreza.loja.masculina.nobreza_loja.DTO.CheckoutFormDto;
import commerce.nobreza.loja.masculina.nobreza_loja.DTO.CheckoutPageDataDTO;
import commerce.nobreza.loja.masculina.nobreza_loja.Entity.Pedido;
import commerce.nobreza.loja.masculina.nobreza_loja.Service.CheckoutService;
import commerce.nobreza.loja.masculina.nobreza_loja.Service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final PedidoService pedidoService;

    @GetMapping("/checkout")
    public String showCheckoutPage(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        CheckoutPageDataDTO checkoutData = checkoutService.carregarDadosCheckout(userDetails.getUsername());

        model.addAttribute("checkoutItems", checkoutData.getCheckoutItems());
        model.addAttribute("totalPrice", checkoutData.getTotalPrice());
        model.addAttribute("enderecos", checkoutData.getEnderecos());
        model.addAttribute("metodosPagamento", checkoutData.getMetodosPagamento());
        model.addAttribute("checkoutFormDto", new CheckoutFormDto());

        return "Checkout";
    }

    @PostMapping("/checkout/processar")
    public String processarCheckout(
            @ModelAttribute CheckoutFormDto form,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            Pedido novoPedido = pedidoService.criarPedidoDoCarrinho(form, userDetails.getUsername());

            return "redirect:/pedido/confirmado/" + novoPedido.getId();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao processar seu pedido: " + e.getMessage());
            return "redirect:/checkout";
        }
    }

    @GetMapping("/pedido/confirmado/{pedidoId}")
    public String showPaginaConfirmacao(
            @PathVariable Long pedidoId,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        if (userDetails == null) {
            return "redirect:/";
        }

        Pedido pedido;
        try {
            pedido = checkoutService.buscarPedidoDoUsuario(pedidoId, userDetails.getUsername());
        } catch (SecurityException ex) {
            return "redirect:/";
        }

        model.addAttribute("pedido", pedido);

        return "closing";
    }
}