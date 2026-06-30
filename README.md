
---

# MecPoint — Gestão Inteligente de Agendamentos

**MecPoint** é uma plataforma backend para gestão inteligente de agendamentos em oficinas mecânicas, conectando clientes e mecânicos de forma eficiente e organizada.

> Para uma visão geral completa do projeto, consulte o slide de apresentação **Mec-Point-Gestão-Inteligente.pdf**, disponível neste repositório.

---

## Sobre o Projeto

O MecPoint permite que clientes agendem serviços para seus veículos com mecânicos cadastrados, enquanto os mecânicos têm acesso a um painel de controle com visão completa de seus agendamentos. A plataforma conta com autenticação segura via JWT e controle de acesso baseado em papéis (roles).

---

## Funcionalidades

- **Autenticação e Autorização** — Login com geração de token JWT e controle de acesso por perfil (`ADMIN`, `MECANICO`, `USER`)
- **Gestão de Usuários** — Cadastro e listagem de clientes e mecânicos
- **Gestão de Veículos** — Cadastro e consulta de veículos por usuário
- **Catálogo de Serviços** — CRUD de tipos de serviços oferecidos pela oficina
- **Agendamentos** — Criação, atualização de status e filtragem por usuário, mecânico, veículo e status
- **Dashboard do Mecânico** — Painel com resumo de agendamentos do mecânico autenticado

---

## Tecnologias Utilizadas

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 3.5.6 |
| Spring Security + JWT | — |
| PostgreSQL | — |
| Flyway | — |
| MapStruct + Lombok | — |
| SpringDoc OpenAPI (Swagger) | — |

---

## Como Executar

### Pré-requisitos

- Java 21+
- Maven
- PostgreSQL em execução


### Executando

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.  
A documentação da API (Swagger UI) estará acessível em `http://localhost:8080/swagger-ui.html`.

---

## Estrutura do Projeto

```
src/main/java/com/mecpoint/
├── agendamento/   # Módulo de agendamentos
├── servico/       # Módulo de serviços
├── user/          # Módulo de usuários e autenticação
├── veiculo/       # Módulo de veículos
└── core/          # Segurança, configurações e utilitários
```

---

---

Você quer que eu escreva esse conteúdo diretamente no arquivo HELP.md ou prefere criar um novo `README.md`?
