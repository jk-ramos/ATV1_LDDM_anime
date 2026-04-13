# Anime Catalog KMP 

Projeto acadêmico desenvolvido com **Kotlin Multiplatform (KMP)**, focando em um sistema de catálogo de animes e personagens com arquitetura cliente-servidor.

## 🎯 Objetivo

Construir uma aplicação completa com tema **Anime**, abrangendo:

  - **Backend:** API robusta com Ktor.
  - **Documentação:** Especificação via Swagger/OpenAPI.
  - **Persistência:** Banco de dados PostgreSQL com controle de versão via Flyway.
  - **Arquitetura:** Compartilhamento de modelos e contratos através do módulo `shared`.
  - **Mobile:** Aplicativo Android nativo consumindo a API real.

-----

## 🛠️ Tecnologias Utilizadas

### Backend

  - **Kotlin** & **Ktor** (Framework Web)
  - **Exposed** (ORM)
  - **PostgreSQL** (Banco de Dados)
  - **Flyway** (Migrations)
  - **Swagger / OpenAPI** (Documentação)

### Mobile

  - **Kotlin Multiplatform (KMP)**
  - **Jetpack Compose** (UI declarativa)
  - **Ktor Client** (Consumo de API)
  - **Android SDK**

### Infraestrutura

  - **Docker Compose** (Orquestração de containers)

-----

## 📂 Estrutura do Projeto

```bash
anime-catalog-kmp/
├── composeApp/                  # Aplicativo Android (Jetpack Compose)
├── server/                      # Backend Ktor
│   └── src/main/
│       ├── kotlin/com/fatec/animecatalog/
│       │   ├── db/              # Configurações de banco e tabelas
│       │   ├── routes/          # Definição dos endpoints
│       │   └── Application.kt   # Ponto de entrada do servidor
│       └── resources/
│           ├── db/migration/    # Scripts SQL do Flyway
│           └── openapi/         # Definições do Swagger
├── shared/                      # Código compartilhado (Models e Interfaces)
├── docker-compose.yml           # Configuração do PostgreSQL
├── .env.example                 # Modelo de variáveis de ambiente
└── README.md
```

-----

## ✨ Funcionalidades Implementadas

### API (Backend)

  - [x] CRUD completo de **Animes**.
  - [x] CRUD completo de **Personagens**.
  - [x] Documentação interativa via **Swagger**.
  - [x] Migrations automatizadas para criação de tabelas.
  - [x] **Seed data** (população inicial do banco) via migration.

### App Android

  - [x] Listagem dinâmica de animes.
  - [x] Exibição de personagens vinculados a cada anime.
  - [x] Interface interativa (expandir/recolher personagens).
  - [x] Busca por nome do anime.
  - [x] Filtro por gênero.

-----

## 📡 Endpoints Principais

### Animes

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/animes` | Lista todos os animes |
| `POST` | `/animes` | Cadastra um novo anime |
| `PUT` | `/animes/{id}` | Atualiza dados de um anime |
| `DELETE` | `/animes/{id}` | Remove um anime |

### Personagens (Characters)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/characters` | Lista todos os personagens |
| `POST` | `/characters` | Cadastra um novo personagem |
| `PUT` | `/characters/{id}` | Atualiza dados de um personagem |
| `DELETE` | `/characters/{id}` | Remove um personagem |

### 📖 Documentação

Com o servidor em execução, acesse:

  - **Swagger UI:** `http://localhost:8080/swagger`
  - **OpenAPI YAML:** `http://localhost:8080/openapi/documentation.yaml`

-----

## 🚀 Como Executar o Projeto

### 1\. Banco de Dados (Docker)

O projeto utiliza PostgreSQL. No diretório raiz, execute:

```bash
# Subir o banco
docker compose up -d

# Parar o banco
docker compose down

# Parar e apagar todos os dados
docker compose down -v
```

### 2\. Configuração de Ambiente

Renomeie o arquivo `.env.example` para `.env` ou configure as variáveis no seu ambiente:

```env
DB_URL=jdbc:postgresql://localhost:5432/animecatalog
DB_USER=devuser
DB_PASSWORD=devpassword
```

### 3\. Executando o Backend

1.  Certifique-se de que o Docker está rodando.
2.  No IntelliJ IDEA, execute a classe `server/src/main/kotlin/.../Application.kt`.
3.  Verifique o funcionamento em `http://localhost:8080/animes`.

### 4\. Executando o App Android

1.  **IP da Máquina:** Verifique o endereço IP local do seu computador (ex: `192.168.0.100`).
2.  **Configuração:** Nos arquivos `AnimeApi.kt` e `CharacterApi.kt`, ajuste a constante:
    ```kotlin
    private const val BASE_URL = "http://<SEU_IP_AQUI>:8080"
    ```
3.  Conecte um dispositivo físico ou emulador e execute o módulo `composeApp`.

-----

## 🗄️ Migrations e Dados Iniciais

As migrations estão localizadas em `server/src/main/resources/db/migration`:

  - `V1`: Criação da tabela de animes.
  - `V2`: Criação da tabela de personagens.
  - `V3`: **Seed data** com títulos como *Naruto*, *Attack on Titan* e *Death Note*, incluindo seus respectivos personagens icônicos.

-----

## 📝 Requisitos Atendidos (Checklist Acadêmico)

  - [x] Mínimo de 2 arquivos de Routes com Ktor.
  - [x] CRUD completo (GET, POST, PUT, DELETE) para cada rota.
  - [x] Documentação Swagger funcional.
  - [x] Módulo `shared` para Models e Repositories.
  - [x] Persistência com Migrations e Seed data.
  - [x] Infraestrutura com Docker Compose.
  - [x] Configuração via Variáveis de Ambiente.
  - [x] Utilização do JetBrains Runtime (jbr-21).


-----

## 👤 Autor

Desenvolvido por **Jaquelaine Ramos** como parte de projeto acadêmico.

-----

*Este projeto foi criado para fins educacionais e de demonstração de tecnologias KMP.*
