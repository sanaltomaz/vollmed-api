# 🏥 Voll Med API

![Java](https://img.shields.io/badge/Java-21-red)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.x-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![JWT](https://img.shields.io/badge/JWT-Authentication-orange)

API REST desenvolvida com Java e Spring Boot como parte da formação Spring Boot 3 da Alura.

Este projeto tem como objetivo estudar a construção de aplicações backend modernas utilizando o ecossistema Spring, aplicando conceitos de APIs REST, persistência de dados, autenticação com JWT, validações e boas práticas de desenvolvimento.

O foco deste repositório é compreender o funcionamento das tecnologias utilizadas por meio da implementação prática de cada etapa do projeto.

---

## 🚀 Funcionalidades

### Médicos

* Cadastro de médicos
* Atualização de dados
* Listagem paginada
* Consulta por ID
* Exclusão lógica (soft delete)
* Listagem de médicos excluídos

### Pacientes

* Cadastro de pacientes
* Atualização de dados
* Listagem paginada
* Consulta por ID
* Exclusão lógica (soft delete)
* Listagem de pacientes excluídos

### Autenticação

* Login de usuários
* Geração de token JWT
* Proteção de endpoints com Spring Security

---

## 🛠 Tecnologias Utilizadas

* Java 21
* Spring Boot 4
* Spring Web
* Spring Security
* Spring Data JPA
* Hibernate
* PostgreSQL
* Flyway
* JWT (JSON Web Token)
* Maven
* Lombok
* Bean Validation

---

## 📚 Conceitos Aplicados

* Desenvolvimento de APIs REST
* Arquitetura em camadas
* Injeção de dependências
* Persistência de dados com JPA
* Paginação de resultados
* Validação de dados
* Tratamento de requisições HTTP
* Soft Delete
* Autenticação e autorização
* Migrations de banco de dados
* Boas práticas com Spring Boot

---

## 📂 Estrutura do Projeto

```text
src/main/java/br/com/alura/vollmed

├── controller
├── domain
│   ├── medico
│   ├── paciente
│   ├── endereco
│   └── usuario
├── infra
│   ├── exception
│   └── security
└── VollmedApiApplication
```

A estrutura busca separar responsabilidades entre domínio da aplicação, infraestrutura e exposição dos endpoints REST.

---

## 🔐 Segurança

A autenticação da aplicação é realizada utilizando:

* Spring Security
* JWT (JSON Web Token)
* Stateless Authentication

Fluxo de autenticação:

```text
Cliente
   │
   ▼
POST /login
   │
   ▼
Validação das credenciais
   │
   ▼
Geração do JWT
   │
   ▼
Token retornado ao cliente
   │
   ▼
Acesso aos endpoints protegidos
```

---

## 📡 Endpoints Disponíveis

### Autenticação

| Método | Endpoint | Descrição                             |
| ------ | -------- | ------------------------------------- |
| POST   | `/login` | Realiza autenticação e retorna um JWT |

---

### Médicos

| Método | Endpoint             | Descrição                   |
| ------ | -------------------- | --------------------------- |
| POST   | `/medicos`           | Cadastra um novo médico     |
| GET    | `/medicos`           | Lista médicos ativos        |
| GET    | `/medicos/{id}`      | Detalha um médico           |
| PUT    | `/medicos/{id}`      | Atualiza dados de um médico |
| DELETE | `/medicos/{id}`      | Realiza exclusão lógica     |
| GET    | `/medicos/deletados` | Lista médicos excluídos     |

---

### Pacientes

| Método | Endpoint               | Descrição                     |
| ------ | ---------------------- | ----------------------------- |
| POST   | `/pacientes`           | Cadastra um novo paciente     |
| GET    | `/pacientes`           | Lista pacientes ativos        |
| GET    | `/pacientes/{id}`      | Detalha um paciente           |
| PUT    | `/pacientes/{id}`      | Atualiza dados de um paciente |
| DELETE | `/pacientes/{id}`      | Realiza exclusão lógica       |
| GET    | `/pacientes/deletados` | Lista pacientes excluídos     |

---

## 📄 Paginação

Os endpoints de listagem utilizam paginação nativa do Spring Data.

Exemplo:

```http
GET /medicos?page=0&size=5&sort=nome
```

```http
GET /pacientes?page=0&size=5&sort=nome
```

Parâmetros disponíveis:

| Parâmetro | Descrição                      |
| --------- | ------------------------------ |
| page      | Página desejada                |
| size      | Quantidade de registros        |
| sort      | Campo utilizado para ordenação |

---

## 🗄 Banco de Dados

O projeto utiliza PostgreSQL como banco principal.

As alterações estruturais são controladas através do Flyway, permitindo versionamento e execução automática das migrations durante a inicialização da aplicação.

---

## ⚙️ Como Executar o Projeto

### 1. Clone o repositório

```bash
git clone https://github.com/sanaltomaz/vollmed-api.git
```

### 2. Acesse a pasta

```bash
cd vollmed-api
```

### 3. Configure o PostgreSQL

Crie um banco de dados e ajuste as propriedades da aplicação:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vollmed_api
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

### 4. Execute a aplicação

Utilizando Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Ou Maven instalado localmente:

```bash
mvn spring-boot:run
```

---

## 🎯 Objetivo de Aprendizado

Este projeto foi desenvolvido para consolidar conhecimentos em desenvolvimento backend com Java e Spring.

A proposta não é apresentar um sistema médico completo, mas construir gradualmente uma aplicação realista enquanto explora recursos importantes do ecossistema Spring, compreendendo como cada componente funciona internamente.

---

## 📖 Referência

Projeto baseado na formação Spring Boot 3 da organização Alura.

Todo o código deste repositório representa minha implementação e evolução prática durante os estudos.

---

## 👨‍💻 Autor

Sanal T. R. da Silva

Estudante de Engenharia da Computação com foco em desenvolvimento backend, arquitetura de software e construção de aplicações utilizando Java e Spring.
