# 🏛 Arquitetura

Este documento descreve a organização da aplicação, as responsabilidades de cada camada e o fluxo de processamento de uma requisição.

## Visão Geral

A aplicação segue uma **arquitetura em camadas**, onde cada componente possui uma responsabilidade bem definida. Essa separação reduz o acoplamento entre as partes do sistema, facilita a manutenção e torna a evolução da aplicação mais previsível.

O fluxo principal de uma requisição é representado pelo diagrama abaixo.

```text
Cliente HTTP
      │
      ▼
 Controller
      │
      ▼
 Service
      │
      ▼
 Validações de Negócio
      │
      ▼
 Repository
      │
      ▼
 PostgreSQL
```

---

# Estrutura do Projeto

```text
src/main/java/br/com/alura/vollmed

├── controller
├── domain
│   ├── consulta
│   ├── medico
│   ├── paciente
│   ├── usuario
│   └── endereco
├── infra
│   ├── exception
│   └── security
├── service
└── VollmedApiApplication
```

Cada pacote possui uma responsabilidade específica.

---

# Camadas da Aplicação

## Controller

Responsável pela exposição da API REST.

Nesta camada são tratados aspectos relacionados ao protocolo HTTP, como:

* recebimento das requisições;
* validação dos dados de entrada;
* conversão entre JSON e objetos Java;
* definição dos códigos de resposta HTTP.

Os controllers não implementam regras de negócio. Seu papel é apenas coordenar a comunicação entre o cliente e a camada de serviço.

---

## Service

A camada de serviço representa os **casos de uso da aplicação**.

É responsável por:

* orquestrar o fluxo da operação;
* coordenar chamadas aos repositórios;
* executar validações de negócio;
* controlar transações quando necessário.

Essa separação evita que controllers acumulem responsabilidades e concentra a lógica da aplicação em um único ponto.

---

## Domain

O pacote `domain` concentra os principais elementos do domínio da aplicação.

Entre eles:

* entidades JPA;
* DTOs de entrada e saída;
* enums;
* repositórios;
* regras específicas de cada módulo.

A organização por domínio aproxima classes relacionadas e facilita a evolução das funcionalidades.

---

## Repository

Os repositórios são responsáveis exclusivamente pelo acesso aos dados.

Utilizando o Spring Data JPA, abstraem consultas ao banco de dados e disponibilizam operações de persistência para a camada de serviço.

---

## Infraestrutura

O pacote `infra` reúne componentes transversais da aplicação, como:

* configuração do Spring Security;
* autenticação baseada em JWT;
* tratamento global de exceções;
* configurações gerais da aplicação.

Esses componentes oferecem suporte às funcionalidades do sistema, mas não fazem parte das regras de negócio.

---

# Fluxo de uma Requisição

O processamento de uma requisição segue, de forma simplificada, o fluxo abaixo.

```text
Cliente

↓

Controller

↓

Validação da requisição

↓

Service

↓

Execução das regras de negócio

↓

Repository

↓

Banco de Dados

↓

Service

↓

Controller

↓

Resposta HTTP
```

Cada camada possui uma responsabilidade específica, evitando que regras de negócio sejam implementadas diretamente nos controllers ou que detalhes de persistência sejam expostos para outras partes da aplicação.

---

# Organização por Domínio

As funcionalidades são agrupadas de acordo com o domínio de negócio.

Cada módulo concentra seus próprios componentes, como entidades, DTOs e repositórios.

Exemplos:

* Médico
* Paciente
* Consulta
* Usuário
* Endereço

Essa organização facilita a localização do código e reduz o acoplamento entre funcionalidades distintas.

---

# Princípios Adotados

Durante o desenvolvimento do projeto foram adotados alguns princípios para orientar a organização do código.

* Separação de responsabilidades.
* Arquitetura em camadas.
* Baixo acoplamento entre componentes.
* Uso de injeção de dependências.
* Validação de dados na entrada da aplicação.
* Encapsulamento das regras de negócio.
* Persistência desacoplada da camada de apresentação.

Esses princípios tornam a aplicação mais simples de manter, testar e evoluir conforme novas funcionalidades são adicionadas.
