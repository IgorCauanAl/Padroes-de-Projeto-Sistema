# Padrões de Projeto: Sistema Nobreza 👑

Este documento cataloga a justificativa arquitetural e os Padrões de Projeto (Design Patterns) implementados no Sistema de E-commerce "Nobreza-Site", criado para fins acadêmicos e desenvolvido em Spring Boot (Java).

---

## 1. Observer Pattern (Padrão Observador)

O padrão Observer define uma dependência um-para-muitos entre objetos, de forma que quando um objeto (o "Sujeito" ou "Subject") muda de estado, todos os seus dependentes ("Observadores" ou "Observers") são notificados e atualizados automaticamente.

### 📌 O Problema Resolvido
No processo natural de um e-commerce, o momento do "Checkout" (quando o cliente finaliza um Pedido) acarreta múltiplas consequências periféricas que não dizem respeito ao núcleo de dados primário do "Pedido", como por exemplo:
1. Limpar permanentemente o carrinho de compras do cliente.
2. Notificar o sistema de e-mail para enviar um recibo da compra.
3. (Possivelmente no futuro) Enviar métricas para um BI, pontuar em um sistema de fidelidade, solicitar baixa no setor de frete.

Sem o padrão Observer, a classe `PedidoService` acabava instanciando e chamando todos os serviços acima de forma procedural e sequencial logo após salvar o Pedido, inflando-a com dependências excessivas (ferindo o `Single Responsibility Principle` do SOLID) e dificultando sua manutenção futura se novos serviços periféricos de checkout fossem adicionados.

### 📍 Localização no Código (Pacotes e Classes)
- **Pacote Base:** `commerce.nobreza.loja.masculina.nobreza_loja.Service`
- **Pacote dos Observers:** `commerce.nobreza.loja.masculina.nobreza_loja.Service.observer`

**Classes Envolvidas:**
1. **`PedidoObserver` (Interface Base):** Define o contrato de inscrição contendo o método `void update(Pedido pedido)`. Ocorre dentro do pacote `observer`.
2. **`EmailConfirmacaoObserver` (Implementação 1):** Ouve um evento de pedido e constrói o envio de confirmação no e-mail real do usuário. Utiliza por debaixo dos panos o `EmailService`.
3. **`LimpezaCarrinhoObserver` (Implementação 2):** Ao receber o aviso de pedido completo, consulta os dados do carrinho ligados àquele usuário e esvazia a fila via `CarrinhoItensRepository`.
4. **`PedidoService` (O Subject):** Representa o núcleo da transação. Agrupa todos aqueles que se assinaram implementando `PedidoObserver` (via Injeção de Dependências Autowired do próprio Spring) numa `List<PedidoObserver>` e os notifica ao final do método `criarPedidoDoCarrinho`.

### 🛠 Como foi aplicado (Exemplo de Implementação)
O sistema valeu-se da capacidade de resolução de contexto de dependências nativa do Spring. 

As implementações assinam a interface publicamente:
```java
@Component
public class EmailConfirmacaoObserver implements PedidoObserver {
    public void update(Pedido pedido) { ... }
}
```

O serviço principal (Subject/Ouvido) recebe todos as classes que implementam validamente o contrato nativamente e só precisa repassar o evento:
```java
@Service
public class PedidoService {
    private final List<PedidoObserver> observers; // O Spring Boot carrega todas implementações de PedidoObserver.

    public Pedido criarPedidoDoCarrinho(CheckoutFormDto form, String userEmail) {
        // ... Logica central de validação e processamento ...
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // Disparo procedural único: Desacoplamento via Observer
        observers.forEach(observer -> observer.update(pedidoSalvo));
        
        return pedidoSalvo;
    }
}
```

### ✨ Benefícios
- **Total Desacoplamento:** O `PedidoService` agora só precisa se preocupar em realizar as validações de pedido e processar o pagamento dele, como seu nome natural sugere e exige.
- **Princípio Aberto-Fechado (OCP):** Um dos alicerces do "SOLID", permitindo que novas funcionalidades reativas a um Checkout sejam adicionadas no futuro ao sistema *sem modificar nem alterar* as linhas já sensíveis de codificação financeira que compõem o `PedidoService`. Basta construir uma nova classe implementando a Interface e decorando com a anotação padrão do ecossistema.
- **Rápida Leitura e Refatoração:** Ao separar as lógicas complexas e periféricas de limpeza e template de e-mails em classes específicas, a manutenibilidade para depurar bugs é verticalmente otimizada.

---

## 2. Facade Pattern (Padrão Fachada)

O Padrão Facade fornece uma interface unificada e de alto nível para um conjunto de interfaces em um subsistema, que o torna mais fácil de usar, escondendo suas complexidades reais das camadas clientes.

### 📌 O Problema Resolvido
No processamento de um cartão de crédito, existe uma alta densidade de etapas invisíveis, como validações antifraude e integração com APIs de operadoras bancárias.
A estratégia de pagamento `CartaoPagamentoStrategy` original teria que conhecer os fluxos de todos esses serviços para concretizar a ação, inflando seu código com infraestrutura onde deveria existir apenas controle de lógica de negócio e regras de checkout (ferindo o `Single Responsibility Principle`).

### 📍 Localização no Código (Pacotes e Classes)
- **Pacote da Fachada:** `commerce.nobreza.loja.masculina.nobreza_loja.Service.facade`
- **Pacote dos Subsistemas:** `commerce.nobreza.loja.masculina.nobreza_loja.Service.facade.subsistema`

**Classes Envolvidas:**
1. **`AntiFraudeService`:** Simula o sistema de validação de fraude baseado no risco/valor.
2. **`GatewayOperadoraService`:** Simula a conexão remota com um banco ou adquirente capturando o saldo do cliente.
3. **`PagamentoCartaoFacade` (A Fachada em si):** É o cérebro que esconde o sistema complexo. Recebe as instâncias e possui um único método direto: `finalizarPagamento(valor)`.
4. **`CartaoPagamentoStrategy` (A Cliente):** Em vez de orquestrar a fraude ou o banco ela mesma, injeta a Fachada e terceiriza inteiramente as chamadas num fluxo blindado e unificado.

### 🛠 Como foi aplicado (Exemplo de Implementação)
As lógicas de bloqueio por fraude e validação restrita com operadoras bancárias foram construídas internamente na classe `PagamentoCartaoFacade`.

O cliente (`CartaoPagamentoStrategy`) apenas invoca confiantemente essa fina camada:
```java
@Component
@RequiredArgsConstructor
public class CartaoPagamentoStrategy implements PagamentoStrategy {

    private final PagamentoCartaoFacade pagamentoCartaoFacade;

    // Chamada única e central
    @Override
    public void processarPagamento(BigDecimal valor) {
        pagamentoCartaoFacade.finalizarPagamento(valor);
    }
}
```

### ✨ Benefícios
- **Interface Simplificada e Intuitiva:** Reduz drasticamente a curva de aprendizado. Novos programadores lidando com Cartão só veem 1 dependência do Facade, e não 30 serviços do banco instanciados.
- **Baixo Acoplamento e Maior Isolamento:** Se a loja decidir amanhã trocar a operadora "Cielo" pela "Stone", a `CartaoPagamentoStrategy` nunca sofrerá *nenhuma* alteração. A adaptação técnica é encapsulada na Facade e no seu subsistema.