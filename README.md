# Padroes-de-Projeto-Sistema
Sistema Nobreza-Site, um simples e-commerce usando alguns padrões de projeto, trabalho do IFBA.

## Fonte canônica do projeto

- Aplicação oficial (backend + frontend servido pelo Spring): `Back-End/nobreza-loja`
- Templates Thymeleaf oficiais: `Back-End/nobreza-loja/src/main/resources/templates`
- CSS/JS/imagens oficiais: `Back-End/nobreza-loja/src/main/resources/static`

## Importante sobre duplicidade de frontend

A pasta `src/` na raiz contém protótipos e versões legadas de páginas estáticas.

- Não usar `src/` raiz como fonte principal para novas features.
- Não editar `Back-End/nobreza-loja/target` (artefato de build).
- Toda evolução funcional deve ocorrer em `Back-End/nobreza-loja/src/main`.

## Execução local

```bash
cd Back-End/nobreza-loja
./mvnw spring-boot:run
```

## Execução com Docker (app + SQL Server)

```bash
cd Back-End/nobreza-loja
cp .env.example .env
docker compose up --build
```

- Aplicação: `http://localhost:8081`
- SQL Server: `localhost:1433`

Para parar:

```bash
docker compose down
```

Para parar removendo também o volume do banco:

```bash
docker compose down -v
```
