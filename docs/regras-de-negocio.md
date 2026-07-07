# 📋 Regras de Negócio

Este documento descreve as regras de negócio implementadas pela aplicação para gerenciamento de médicos, pacientes e consultas.

As regras representam as restrições e validações necessárias para garantir a consistência dos dados e o correto funcionamento do sistema.

---

# Médicos

## Cadastro

Para que um médico seja cadastrado é necessário informar:

* Nome
* E-mail
* Telefone
* CRM
* Especialidade
* Endereço

As informações devem atender às validações definidas pela aplicação.

---

## Atualização

Um médico pode atualizar apenas seus dados cadastrais.

O identificador (ID) permanece imutável após o cadastro.

---

## Exclusão

A remoção de médicos é realizada por meio de **Soft Delete**.

O registro permanece armazenado no banco de dados, sendo apenas marcado como inativo.

Essa abordagem preserva o histórico da aplicação e evita perda definitiva de informações.

---

# Pacientes

## Cadastro

Para que um paciente seja cadastrado é necessário informar:

* Nome
* E-mail
* Telefone
* CPF
* Endereço

As informações devem atender às validações definidas pela aplicação.

---

## Atualização

Os dados cadastrais do paciente podem ser atualizados sem alterar sua identidade dentro do sistema.

---

## Exclusão

Assim como ocorre com os médicos, a exclusão de pacientes é realizada utilizando **Soft Delete**.

---

# Consultas

O módulo de consultas concentra a maior parte das regras de negócio da aplicação.

## Agendamento

Uma consulta somente pode ser agendada quando todas as condições abaixo forem atendidas.

### Médico ativo

O médico selecionado deve estar ativo no sistema.

Consultas não podem ser agendadas para médicos inativos.

---

### Paciente ativo

O paciente informado deve estar ativo.

Pacientes inativos não podem realizar novos agendamentos.

---

### Disponibilidade do médico

Um médico não pode possuir duas consultas marcadas para o mesmo horário.

---

### Disponibilidade do paciente

Um paciente não pode possuir duas consultas agendadas para o mesmo horário.

---

### Especialidade

Quando o médico não é informado explicitamente, a aplicação seleciona automaticamente um profissional disponível da especialidade solicitada.

Caso não exista médico disponível, o agendamento é recusado.

---

# Cancelamento de Consultas

Uma consulta somente pode ser cancelada respeitando as regras abaixo.

## Antecedência mínima

O cancelamento deve ocorrer com pelo menos **24 horas de antecedência** em relação ao horário agendado.

Caso essa condição não seja atendida, a operação é rejeitada.

---

## Motivo do cancelamento

Todo cancelamento deve possuir um motivo informado pelo usuário.

Esse motivo é armazenado juntamente com o cancelamento da consulta.

---

# Validação dos Dados

Além das regras de negócio, a aplicação realiza validações dos dados recebidos pelas requisições.

Entre elas:

* campos obrigatórios;
* formato de e-mail;
* formato de CPF;
* formato de CRM;
* valores nulos;
* consistência dos dados de entrada.

Essas validações impedem que informações inválidas sejam processadas pela aplicação.

---

# Objetivo das Regras

As regras de negócio têm como finalidade garantir que todas as operações realizadas pela aplicação respeitem as restrições do domínio, preservando a integridade dos dados e assegurando um comportamento consistente em todas as funcionalidades do sistema.
