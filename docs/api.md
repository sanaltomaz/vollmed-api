# 📖 API

Este documento apresenta uma visão geral da API, seu processo de autenticação e como acessar a documentação interativa gerada automaticamente.

A documentação detalhada dos endpoints é disponibilizada pelo **SpringDoc OpenAPI**, garantindo que permaneça sincronizada com a implementação da aplicação.

---

# URL Base

Durante o desenvolvimento, a aplicação é executada localmente utilizando:

```text
http://localhost:8080
```

---

# Autenticação

A API utiliza autenticação baseada em **JSON Web Token (JWT)**.

O acesso aos endpoints protegidos exige um token obtido após a autenticação do usuário.

## Fluxo de autenticação

```text
Cliente

↓

POST /login

↓

Validação das credenciais

↓

Geração do JWT

↓

Retorno do token

↓

Requisições autenticadas
```

Após receber o token, ele deve ser enviado no cabeçalho HTTP das requisições protegidas.

```http
Authorization: Bearer <token>
```

---

# Formato das Requisições

A API utiliza o formato **JSON** para comunicação.

Exemplo de cabeçalhos:

```http
Content-Type: application/json
Accept: application/json
```

---

# Paginação

Os endpoints de listagem utilizam a paginação nativa do Spring Data.

Exemplo:

```http
GET /medicos?page=0&size=10&sort=nome
```

Parâmetros disponíveis:

| Parâmetro | Descrição                          |
| --------- | ---------------------------------- |
| `page`    | Página desejada                    |
| `size`    | Quantidade de registros por página |
| `sort`    | Campo utilizado para ordenação     |

---

# Códigos de Resposta

A API utiliza os códigos HTTP convencionais para representar o resultado das operações.

| Código             | Significado                                  |
| ------------------ | -------------------------------------------- |
| `200 OK`           | Operação realizada com sucesso               |
| `201 Created`      | Recurso criado com sucesso                   |
| `204 No Content`   | Operação realizada sem conteúdo de retorno   |
| `400 Bad Request`  | Dados inválidos ou regra de negócio violada  |
| `401 Unauthorized` | Usuário não autenticado                      |
| `403 Forbidden`    | Usuário sem permissão para acessar o recurso |
| `404 Not Found`    | Recurso não encontrado                       |

---

# Tratamento de Erros

A aplicação possui um tratamento global de exceções para padronizar as respostas enviadas ao cliente.

Erros de validação, autenticação e regras de negócio são convertidos em respostas HTTP apropriadas, facilitando o consumo da API por aplicações clientes.

---

# Observações

A documentação apresentada pelo Swagger/OpenAPI deve ser considerada a principal referência para consulta dos endpoints, parâmetros e modelos de dados, uma vez que é gerada automaticamente a partir da implementação da aplicação e permanece sincronizada com sua evolução.
