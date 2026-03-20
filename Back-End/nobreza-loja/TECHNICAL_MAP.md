# Mapa Técnico — Sistema Nobreza

## Fonte canônica
- Backend e frontend server-side: `Back-End/nobreza-loja/src/main`
- Templates: `Back-End/nobreza-loja/src/main/resources/templates`
- Assets estáticos: `Back-End/nobreza-loja/src/main/resources/static`
- Build output (não editar): `Back-End/nobreza-loja/target`

## Matriz módulo → rota → camadas

### Home
- Rotas: `/page_principal`, `/api/page_principal`
- Controller: `PagePrincipalController`
- Service: `ProductService`
- Repositório: `ProductRepository`
- Template: `templates/page_principal.html`

### Catálogo
- Rota: `/api/catalog/page`
- Controller: `CatalogController`
- Service: `ProductService`
- Repositório: `ProductRepository`
- Template: `templates/catalog.html`

### Produto
- Rota: `/produto/{id}`
- Controller: `ClothesController`
- Service: `ProductService`
- Repositório: `ProductRepository`
- Template: `templates/clothes.html`

### Carrinho
- Rotas: `/api/cart/add`, `/api/cart/remove/{id}`, `/api/cart/items`
- Controller: `CarrinhoController`
- Service: `CarrinhoService`
- Repositório: `CarrinhoItensRepository`

### Checkout
- Rotas: `/checkout`, `/checkout/processar`, `/pedido/confirmado/{pedidoId}`
- Controller: `CheckoutController`
- Services: `CheckoutService`, `PedidoService`
- Repositórios: `PedidoRepository`, `EnderecoRepository`, `MetodoPagamentoRepository`, `UserRepository`
- Template: `templates/Checkout.html`, `templates/closing.html`

### Perfil do usuário
- Rotas: `/user/manager`, `/user/pedido/{id}`
- Controller: `UseController`
- Services: `UserService` (+ serviços de conta)
- Repositórios: `UserRepository`, `PedidoRepository`
- Template: `templates/PerfilUSER.html`

### Administração
- Rotas: `/manageruser`, `/admin/usuarios/promover/{id}`, `/admin/usuarios/deletar/{id}`
- Controller: `ADMController`
- Service: `ADMUserService`
- Repositórios: `UserRepository`, `RoleRepository`, `PedidoRepository`
- Template: `templates/PerfilADM.html`

### Busca
- Rota API: `/api/search/suggest`
- Controller: `SearchController`
- Service: `SearchService`
- Repositórios: `ProductRepository`, `CategoryRepository`

### IA/Chatbot
- Rota API: `/api/ai/chat`
- Controller: `AiController`
- Service: `AiService`

## Convenções de evolução
1. Toda nova regra de negócio deve entrar em Service, não em Controller.
2. Controller deve coordenar fluxo HTTP, validação de entrada e resposta.
3. Template deve apontar apenas para rotas existentes em Controller.
4. Evitar hardcode de autorização por email; usar role (`ROLE_ADMIN`, etc).
5. Não editar arquivos em `target`.

## Checklist de revisão (PR)
- [ ] Rotas usadas nos templates existem nos controllers.
- [ ] Não há paths sensíveis a case quebrando no Linux.
- [ ] Controller não acessa repositório diretamente (exceto exceções aprovadas).
- [ ] Permissões validadas por role no SecurityConfig.
- [ ] Build `mvnw -DskipTests compile` passa sem erro.
- [ ] Nenhuma alteração foi feita em `target`.
