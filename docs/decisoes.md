# 🧠 Decisões Arquiteturais

Este documento reúne as principais decisões adotadas durante o desenvolvimento da aplicação e as motivações técnicas por trás de cada escolha.

O objetivo é registrar os critérios utilizados na organização do projeto e facilitar futuras evoluções.

---

# Arquitetura em Camadas

A aplicação foi organizada em camadas para separar responsabilidades entre apresentação, regras de negócio, persistência e infraestrutura.

Essa divisão reduz o acoplamento entre componentes, melhora a legibilidade do código e facilita a manutenção da aplicação.

---

# Controllers Enxutos

Os controllers são responsáveis apenas pela comunicação HTTP.

Seu papel limita-se a:

* receber requisições;
* validar os dados de entrada;
* delegar a execução para a camada de serviço;
* retornar a resposta ao cliente.

As regras de negócio permanecem fora da camada de apresentação.

---

# Camada de Serviço

A lógica da aplicação é centralizada na camada de serviço.

Essa camada coordena os casos de uso, executa validações de negócio e interage com os repositórios, evitando que controllers concentrem responsabilidades que não pertencem à comunicação HTTP.

---

# Organização por Domínio

As funcionalidades são agrupadas por domínio da aplicação.

Cada módulo reúne os componentes relacionados à sua responsabilidade, facilitando a localização do código e reduzindo a dependência entre funcionalidades distintas.

---

# Spring Data JPA

O acesso aos dados é realizado utilizando o Spring Data JPA.

A abstração oferecida pelo framework reduz código repetitivo, facilita consultas ao banco de dados e permite concentrar o esforço de desenvolvimento nas regras de negócio.

---

# Flyway

O versionamento do banco de dados é realizado com Flyway.

Cada alteração estrutural é registrada como uma migration, garantindo que diferentes ambientes permaneçam sincronizados e que a evolução do esquema do banco seja controlada.

---

# Soft Delete

Médicos e pacientes não são removidos fisicamente do banco de dados.

Em vez disso, os registros são marcados como inativos.

Essa estratégia preserva o histórico da aplicação, evita perda definitiva de informações e reduz riscos relacionados à integridade dos dados.

---

# JWT para Autenticação

A autenticação utiliza JSON Web Tokens (JWT).

Após a validação das credenciais, um token é emitido e utilizado nas requisições subsequentes, permitindo que a aplicação opere de forma stateless.

---

# Spring Security

O controle de autenticação e autorização é realizado pelo Spring Security.

Sua integração com JWT fornece uma solução consolidada para proteção dos endpoints da aplicação.

---

# Bean Validation

As validações de entrada são realizadas utilizando Bean Validation.

Essa abordagem centraliza regras básicas de validação, reduz verificações repetidas no código e melhora a qualidade das respostas enviadas pela API.

---

# DTOs

Os dados recebidos e retornados pela API são representados por DTOs.

Essa separação evita a exposição direta das entidades de persistência e permite maior controle sobre o contrato da API.

---

# PostgreSQL

O PostgreSQL foi adotado como banco de dados relacional da aplicação por sua robustez, ampla adoção no mercado e integração com o ecossistema Spring.

---


# Testes Automatizados

O projeto inclui testes automatizados para validar o comportamento de componentes da aplicação.

Foram utilizados recursos como JUnit, Mockito e MockMvc para verificar regras de negócio, persistência e comportamento dos endpoints.

---

# Evolução Contínua

As decisões apresentadas neste documento refletem o estado atual do projeto.

Conforme novas funcionalidades forem implementadas ou a arquitetura evoluir, este documento deverá ser atualizado para registrar as motivações técnicas das mudanças realizadas.
