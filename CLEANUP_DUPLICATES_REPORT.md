# Relatório de Limpeza Controlada de Duplicidades

## Objetivo
Remover arquivos legados duplicados em `src/` sem impacto no runtime da aplicação oficial.

## Escopo da verificação
- Fontes canônicas consideradas:
  - `Back-End/nobreza-loja/src/main/resources/templates`
  - `Back-End/nobreza-loja/src/main/resources/static`
- Fonte legado considerada:
  - `src/` (raiz do repositório)
- Extensões analisadas:
  - `.html`, `.css`, `.js`

## Checklist de segurança executado
- [x] Verificada ausência de referências de runtime para caminhos de `src/` legado dentro do backend.
- [x] Remoção restrita a arquivos legados com nome duplicado já presente na fonte canônica.
- [x] Nenhum arquivo removido da fonte canônica (`Back-End/nobreza-loja/src/main/...`).
- [x] Build de validação executado após limpeza.

## Resultado
- Arquivos legados removidos: **28**
- Estratégia: remoção por correspondência de nome de arquivo (case-insensitive) para `html/css/js`.

## Lista removida
- `src/Catalog/Catalog.css`
- `src/Checkout-Information/Checkout.css`
- `src/Checkout-Information/Checkout.html`
- `src/Checkout-Information/Checkout.js`
- `src/Clothes/Sale-section.css`
- `src/Clothes/clothe.js`
- `src/Perfil/ADM/PerfilADM.css`
- `src/Perfil/ADM/PerfilADM.html`
- `src/Perfil/ADM/PerfilADM.js`
- `src/Perfil/Perfil.css`
- `src/Perfil/Perfil.html`
- `src/Perfil/Perfil.js`
- `src/Purchase_closing_page/purchaseclosure.css`
- `src/Screen Login/login.css`
- `src/Screen Login/login.html`
- `src/Screen Login/login.js`
- `src/footer.css`
- `src/page-principal-ecommerce/Categories.css`
- `src/page-principal-ecommerce/Coupon.css`
- `src/page-principal-ecommerce/Footer.css`
- `src/page-principal-ecommerce/Insert-information.css`
- `src/page-principal-ecommerce/JS/Categories.js`
- `src/page-principal-ecommerce/JS/MostWanted.js`
- `src/page-principal-ecommerce/JS/News.js`
- `src/page-principal-ecommerce/JS/page-principal.js`
- `src/page-principal-ecommerce/MostWanted.css`
- `src/page-principal-ecommerce/News.css`
- `src/page-principal-ecommerce/Page-Principal.CSS`

## Observação
A pasta `src/` segue no repositório como legado para referência histórica, conforme `src/README_LEGACY.md`.
