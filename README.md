# 🏥 Voll.med API


![Java](https://img.shields.io/badge/Java-21-red)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.x-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)
![JWT](https://img.shields.io/badge/JWT-Authentication-orange)

API REST desenvolvida em **Java** e **Spring Boot** para gerenciamento de médicos, pacientes e consultas médicas.

O projeto foi construído com foco na aplicação de boas práticas de desenvolvimento backend, arquitetura em camadas, autenticação com JWT, persistência de dados, validações de negócio e documentação de APIs utilizando o ecossistema Spring.

---

# ✨ Funcionalidades

### Autenticação

* Login de usuários
* Geração de JWT
* Proteção de endpoints com Spring Security

### Médicos

* Cadastro
* Atualização
* Listagem paginada
* Consulta por ID
* Exclusão lógica (Soft Delete)
* Listagem de registros excluídos

### Pacientes

* Cadastro
* Atualização
* Listagem paginada
* Consulta por ID
* Exclusão lógica (Soft Delete)
* Listagem de registros excluídos

### Consultas

* Agendamento de consultas
* Cancelamento de consultas
* Validações de regras de negócio

---

# 🛠 Tecnologias

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* PostgreSQL
* Flyway
* JWT (JSON Web Token)
* Maven
* Lombok
* Bean Validation
* JUnit 5
* Mockito
* MockMvc

---

# 🏛 Arquitetura

O projeto segue uma arquitetura em camadas, separando responsabilidades entre exposição da API, regras de negócio, persistência e infraestrutura.

```text
Cliente HTTP
      │
      ▼
 Controllers
      │
      ▼
   Services
      │
      ▼
 Validações de Negócio
      │
      ▼
 Repositories
      │
      ▼
 PostgreSQL
```

Uma descrição mais detalhada da arquitetura pode ser encontrada em:

* 📄 **[Arquitetura](docs/arquitetura.md)**

---

# 📁 Estrutura do Projeto

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
│   ├── security
│   ├── exception
├── service
└── VollmedApiApplication
```

---

# 📚 Documentação

A documentação técnica foi organizada em documentos independentes para manter este README objetivo.

| Documento                                             | Descrição                                                                      |
| ----------------------------------------------------- | ------------------------------------------------------------------------------ |
| 📐 **[Arquitetura](docs/arquitetura.md)**             | Organização da aplicação, responsabilidades das camadas e fluxo de requisições |
| 📋 **[Regras de Negócio](docs/regras-de-negocio.md)** | Regras implementadas para médicos, pacientes e consultas                       |
| 🧠 **[Decisões Arquiteturais](docs/decisoes.md)**     | Principais decisões de projeto e justificativas técnicas                       |
| 📖 **[API](docs/api.md)**                             | Documentação da API                           |

---

# 🔐 Segurança

A autenticação da aplicação utiliza:

* Spring Security
* JWT (JSON Web Token)
* Autenticação Stateless

---

# 🚀 Executando o Projeto

## 1. Clone o repositório

```bash
git clone https://github.com/sanaltomaz/vollmed-api.git
```

## 2. Acesse o diretório

```bash
cd vollmed-api
```

## 3. Configure o banco de dados

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vollmed_api
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## 4. Execute a aplicação

Com o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Ou utilizando o Maven instalado:

```bash
mvn spring-boot:run
```

---

# 🎯 Objetivo

Este projeto foi desenvolvido como parte da formação em Spring Boot, evoluindo gradualmente para incorporar práticas utilizadas em aplicações backend reais, como separação de responsabilidades, autenticação baseada em JWT, validações de domínio, documentação automática da API e testes automatizados.

Além da implementação funcional, o foco do projeto está na compreensão das decisões arquiteturais e dos fundamentos das tecnologias utilizadas.

---

# 👨‍💻 Autor

**Sanal Tomaz Roza da Silva**

Estudante de Engenharia da Computação com foco em desenvolvimento Backend Java, arquitetura de software e construção de aplicações utilizando o ecossistema Spring.
