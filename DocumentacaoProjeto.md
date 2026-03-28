# Documentação de Arquitetura e Padrões de Projeto: Sistema Nobreza 👑

Este documento cataloga a justificativa arquitetural e os Padrões de Design implementados no Sistema de E-commerce Nobreza, desenvolvido em Spring Boot (Java) para fins acadêmicos.

---

## 🏛️ Padrões Arquiteturais

A macroestrutura do software foi desenhada para garantir manutenibilidade, testabilidade e separação de responsabilidades.

### 1. MVC (Model-View-Controller)
O sistema adota os preceitos do MVC Clássico, separando de forma clara a interface de usuário da lógica de negócios e da representação real de banco de dados.
- **Model:** Nossas entidades JPA (como `Produto`, `Pedido`, `Usuario`) e abstrações locais representam a base de dados.
- **Controller:** As classes anotadas com `@RestController` que capturam as chamadas HTTP (POST, GET) de usuários e repassam para as regras de negócio nas camadas inferiores.
- **View:** As páginas estáticas Thymeleaf e os frameworks Web, que desenham os dados repassados pelo Controller para o dispositivo do cliente.

### 2. Arquitetura em Camadas (Layered Pattern)
Mesmo utilizando MVC, o projeto expande a divisão interna para uma Arquitetura em Camadas rígida para evitar "Controllers Gordos". 
- `Controller (Apresentação)` -> Comunica com `Service`
- `Service (Regra de Negócios)` -> Realiza a lógica centralizada e orquestra chamadas.
- `Repository (Acesso aos Dados)` -> Contratos Spring Data JPA (`interface ProductRepository extends JpaRepository`).
- O fluxo dita que um Controller *nunca* deve acessar um Repository diretamente sem passar pelo Service.

---

## 🏗️ Padrões Criacionais (Creational Patterns)
Lidam com os mecanismos de criação e instanciação de objetos de forma independente do sistema.

### 1. Builder Pattern (Construtor)
O padrão **Builder** provê uma forma flexível de extrair lógicas monstruosas de construção para objetos complexos, resolvendo o chamado *Anti-Pattern de Construtor Telescópico*.
- **O Problema:** A entidade `Produto` deste sistema possui quase 15 campos (`nome`, `preco`, `quantidade`, `desconto`, `categoria`, coleções de `imagens` e `cores`). Exigir a injeção ordenada desses atributos num `.new Produto(nome, preco...)` causaria lentidão, quebra de compilação ou inserções na ordem trocada.
- **Como foi Aplicado:** Através do framework *Lombok* utilizando a anotação `@Builder` na classe `Produto` (em `Entity/Produto.java`). 
- **Benefícios:** A instanciação por parte dos `Services` pode omitir campos nulos de forma limpa, baseando-se no preenchimento nomeado seguro `Produto.builder().name("Camisa").price(50).build()`.

---

## 🧩 Padrões Estruturais (Structural Patterns)
Lidam com a forma como classes e objetos são compostos para formar estruturas e interfaces maiores.

### 2. Facade Pattern (Fachada)
Fornece uma interface unificada e simplificada para um conjunto complexo de subsistemas periféricos.
- **Localização:** `Service.facade.PagamentoCartaoFacade`
- **Como foi Aplicado:** No checkout de cartões, um simples pagamento necessita validar regras antifraude complexas (`AntiFraudeService`) e consumir APIs bancárias pesadas (`GatewayOperadoraService`). O `PagamentoCartaoFacade` injeta estes sistemas pesados e expõe apenas um método `finalizarPagamento()`.
- **Benefícios:** O cliente (`CartaoPagamentoStrategy`) é brutalmente simplificado e isolado em dependências, sem necessitar aprender como o Sistema Financeiro opera por trás das cortinas.

### 3. Proxy Pattern (Procurador)
Fornece um substituto ou espaço reservado (placeholder) para outro objeto gerenciar o acesso a ele, otimizando ou segurando custos.
- **Localização:** `Service.proxy.ProductCatalogProxy` implementando `CatalogoServiceInterface`.
- **Como foi Aplicado:** Aqui é aplicado na modalidade **Cache Proxy**. O serviço verdadeiro (`ProductService`) pode gerar uma sobrecarga massiva no Banco de Dados se milhares de usuários clicarem no catálogo principal. Então, injetamos pelo Spring Boot o `@Primary ProductCatalogProxy`, possobilitar checagem no `CacheManager`. Se e somente se o Cache der "Miss", ele autoriza o `ProductService` real a sofrer carga.
- **Benefícios:** Alívio profundo de performance (IO e Network do SQL), escalando a aplicação significativamente mediante leitura repetida.

---

## ⚙️ Padrões Comportamentais (Behavioral Patterns)
Lidam com a comunicação, responsabilidades pontuais e fluxos dinâmicos em tempo de execução entre objetos.

### 4. Strategy Pattern (Estratégia)
Permite definir uma família de algoritmos, encapsular cada um isoladamente e torná-los intercambiáveis sem afetar como os componentes maiores operam com ele.
- **Localização:** Interface `PagamentoStrategy` e sua ramificação de implementações injetáveis.
- **Como foi Aplicado:** Para o e-commerce, o cliente pode pagar na forma `CartaoPagamentoStrategy`, `PixPagamentoStrategy` ou em boleto. Em vez do serviço injetar pesados e poluentes `if (modo == PIX) {...}` no núcleo, criamos contratos. O Contexto do Spring recebe a interface em alta abstração e delega ao filho que foi sinalizado no request.
- **Benefícios:** Respeito absoluto ao Princípio de Responsabilidade Única (SRP) e ao Princípio Aberto-Fechado (Open-Closed). Novas formas de pagamento no futuro não causarão quebras na classe Original `Pedido`.

### 5. Observer Pattern (Observador)
Define uma dependência um-para-muitos para que quando um sujeito (publisher) seja alterado, reações passivas aconteçam automaticamente.
- **Localização:** `Service.observer` com suas implementações `LimpezaCarrinhoObserver` e `EmailConfirmacaoObserver`.
- **Como foi Aplicado:** O serviço natural de e-commerce da aplicação acarreta eventos colaterais automáticos mediante a venda de uma roupa (limpar o carrinho, alertar ao cliente, disparar rotinas de nota fiscal...). 
- **Benefícios:** O core do objeto Transacional (`PedidoService`) fica 100% livre. Ele apenas "grita em uma praça pública" usando o método abstrato que ele finalizou seu fluxo e todas as classes injetadas ouvintes (`PedidoObserver`) tomam conhecimento da situação acionando as consequências de forma independente.