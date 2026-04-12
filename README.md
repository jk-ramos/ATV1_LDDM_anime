# Anime Catalog KMP + Ktor

Projeto desenvolvido com Kotlin Multiplatform + Ktor, utilizando PostgreSQL com Docker Compose, migrations com Flyway,
documentação com Swagger e models/repositories compartilhados no módulo `shared`.

## Tecnologias

- Kotlin Multiplatform
- Ktor
- PostgreSQL
- Docker Compose
- Flyway
- Exposed
- Swagger / OpenAPI

## Estrutura

- `shared`: models e interfaces de repository
- `server`: backend Ktor, repositories Exposed, migrations, rotas e swagger
- `composeApp`: módulo do app

## Requisitos

- IntelliJ IDEA
- Gradle JVM: `jbr-21 JetBrains Runtime`
- Docker Desktop
- JDK 21

## Configuração

Copie o arquivo `.env.example` para `.env`:

```env
DB_URL=jdbc:postgresql://localhost:5432/animecatalog
DB_USER=devuser
DB_PASSWORD=devpassword
SERVER_PORT=8080