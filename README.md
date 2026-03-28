# 🛍️ Nobreza E-Commerce

Bem-vindo ao repositório do **Nobreza-Site**, um sistema de comércio eletrônico focado em moda masculina, desenvolvido como projeto acadêmico do IFBA. 

Este software foi projetado para simular uma vitrine digital completa, oferecendo carrinho de compras, controle de usuários e gestão de pedidos por debaixo de uma arquitetura limpa em Spring Boot.

## 🚀 Tecnologias Utilizadas
A paleta de tecnologias e bibliotecas adotadas foca em performance, padronização e facilidade de deploy:
- **Backend**: Java / Spring Boot (MVC)
- **Frontend Servido**: Templates dinâmicos Thymeleaf + HTML/CSS/JS nativos
- **Banco de Dados**: SQL Server (em container Docker)
- **Boilerplate**: Lombok (para getters/setters e builders automatizados)

---

## ⚙️ Como Executar o Projeto

O sistema está alocado integralmente dentro do diretório `/Back-End/nobreza-loja`. Todo desenvolvimento e execução de comandos deve se originar dessa pasta base.

Você tem duas opções para iniciar os serviços na sua máquina local:

### Opção 1: Via Docker (Recomendado)
A forma mais rápida, pois automatiza e sobe simultaneamente a aplicação e o Banco de Dados.
```bash
# 1. Entre no diretório da aplicação principal
cd Back-End/nobreza-loja

# 2. Copie o arquivo de variáveis de ambiente do molde
cp .env.example .env

# 3. Suba as instâncias via Docker Compose
docker compose up --build
```
> O site Nobreza estará disponível em [http://localhost:8081](http://localhost:8081).
> O Banco de Dados SQL Server alocará a porta `1433`.

*Para interromper a execução:* `docker compose down`

### Opção 2: Via Wrapper Maven (Apenas Aplicação Local)
Se você já possui o banco rodando por fora e só deseja subir o serviço Java:
```bash
# 1. Entre na pasta raiz
cd Back-End/nobreza-loja

# 2. Execute a Spring Boot Application 
./mvnw spring-boot:run
```

---

## 🏛️ Arquitetura e Engenharia de Software

Este aplicativo aplica fortíssimos conceitos de Clean Code, SOLID e Design Patterns para assegurar manutenibilidade. A estrutura principal MVC foi modelada junto a Padrões GoF rígidos da computação:

* **Criacionais**: *Builder*
* **Estruturais**: *Facade* e *Proxy*
* **Comportamentais**: *Strategy* e *Observer*

📚 **Consulte [DocumentacaoProjeto.md](./DocumentacaoProjeto.md)** para instruções profundas, exemplos em código e discussões sobre o design arquitetural usado neste software e quais problemas cada padrão de projeto está resolvendo simultaneamente nos bastidores dessa loja.

---
> ⚠️ **Aviso de Duplicidade Frontend:** A pasta `src/` que está listada na raiz extrema do repositório contém protótipos e versões não integradas de HTML estático. Toda evolução verdadeira e rotas dinâmicas do projeto ocorrem estritamente dentro de `Back-End/nobreza-loja/src/main`. Favor ignorar as pastas root legadas.
