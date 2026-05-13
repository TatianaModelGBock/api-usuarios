# 🛒 API de Usuários, Produtos e Caixa

API REST desenvolvida em Java com Spring Boot como projeto de estudo de arquitetura em camadas (Controller, Service, Repository), DTOs, persistência com JPA, ordenação, idempotência, controle de concorrência, controle de estoque e módulo financeiro.

---

## 🚀 Tecnologias utilizadas

| Tecnologia | Versão | Para que serve |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.x | Framework para criar a API |
| Spring Web | — | Mapeamento de rotas REST |
| Spring Data JPA | — | Persistência de dados no banco |
| Hibernate | — | Implementação do JPA |
| H2 Database | — | Banco de dados em memória |
| Maven | — | Gerenciamento de dependências |

---

## 🏗️ Arquitetura

O projeto segue o padrão de camadas MVC com camadas auxiliares:

```
Requisição HTTP
      ↓
  Controller        ← recebe a requisição e devolve a resposta
      ↓
    DTO             ← filtra os dados de entrada
      ↓
  Service           ← aplica as regras de negócio
      ↓
 Repository         ← acessa o banco de dados
      ↓
   Model            ← representa a tabela no banco
```

- **Controller** — é a porta de entrada da API. Recebe requisições HTTP, repassa para o Service e devolve a resposta. Não tem lógica de negócio.
- **DTO (Data Transfer Object)** — envelope de dados. Define exatamente o que pode entrar e sair da API, sem expor a estrutura interna do banco.
- **Service** — é onde ficam as regras de negócio. Valida dados, coordena operações e chama o Repository.
- **Repository** — acessa o banco de dados. Com Spring Data JPA, os métodos `save()`, `findAll()`, `findById()` e `deleteById()` são gerados automaticamente.
- **Model/Entity** — representa uma tabela do banco de dados em forma de classe Java.

---

## 📦 Endpoints

### Usuários `/usuarios`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/usuarios` | Cria um novo usuário |
| GET | `/usuarios` | Lista todos os usuários ordenados por nome (A→Z) |
| PUT | `/usuarios/{id}` | Atualiza os dados de um usuário |
| DELETE | `/usuarios/{id}` | Remove um usuário |

**Body para criar/atualizar:**
```json
{
  "nome": "Tatiana",
  "email": "tatiana@email.com"
}
```

**Regras:**
- Email deve ser único — não é possível cadastrar dois usuários com o mesmo email
- Retorna `400 Bad Request` se o email já estiver cadastrado

---

### Produtos `/produtos`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/produtos` | Cria um novo produto |
| GET | `/produtos` | Lista todos os produtos ordenados por preço (menor→maior) |
| PUT | `/produtos/{id}` | Atualiza os dados de um produto |
| DELETE | `/produtos/{id}` | Remove um produto |
| PATCH | `/produtos/{id}/venda?quantidade={n}` | Baixa o estoque após uma venda |

**Body para criar/atualizar:**
```json
{
  "nome": "Café",
  "preco": 5.0,
  "quantidadeEstoque": 10,
  "estoqueMinimo": 3
}
```

**Regras:**
- Nome deve ser único — não é possível cadastrar dois produtos com o mesmo nome
- Preço deve ser maior que zero
- Estoque e estoque mínimo não podem ser negativos
- Retorna `400 Bad Request` se o estoque for insuficiente para a venda

---

### Caixa `/caixa`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/caixa/vendas` | Inicia uma nova venda vazia |
| POST | `/caixa/vendas/{id}/itens` | Adiciona um item à venda |
| GET | `/caixa/vendas/{id}` | Busca os detalhes de uma venda |
| POST | `/caixa/vendas/{id}/fechar` | Fecha a venda com pagamento |

**Body para adicionar item:**
```json
{
  "produtoId": 1,
  "quantidade": 2
}
```

**Body para fechar venda:**
```json
{
  "valorRecebido": 20.0
}
```

**Regras:**
- Não é possível adicionar itens a uma venda já finalizada
- O valor recebido deve ser maior ou igual ao total da venda
- Quantidade deve ser maior que zero

---

### Módulo Financeiro `/api/financeiro`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/financeiro/pagar` | Registra uma nova transação de pagamento |
| PUT | `/api/financeiro/confirmar/{id}` | Confirma uma transação pendente |

**Body para registrar pagamento:**
```json
{
  "vendaId": 1,
  "valor": 150.50,
  "chaveIdempotencia": "pagamento-001"
}
```

**Ciclo de vida da transação:**
```
PENDENTE → CONCLUIDA
         → FALHA
```

**Regras:**
- Valor deve ser maior que zero — usa `BigDecimal` para precisão financeira
- `chaveIdempotencia` é obrigatória e deve ser única — impede pagamentos duplicados
- Retorna `400 Bad Request` se a chave de idempotência já foi usada

---

## ✅ Funcionalidades implementadas

### 🔤 Ordenação com Comparator
Os resultados dos endpoints `GET` são ordenados automaticamente:
- Usuários — ordem alfabética por nome (A→Z)
- Produtos — ordem crescente por preço (menor→maior)

### 🔒 Idempotência
Garante que a mesma operação não cause efeitos duplicados:
- Cadastro de usuário valida se o email já existe antes de criar
- Cadastro de produto valida se o nome já existe antes de criar
- Módulo financeiro valida a `chaveIdempotencia` — impede que o mesmo pagamento seja processado duas vezes

### ⚡ Controle de concorrência com @Version
Protege contra o problema de "atualização perdida" quando dois usuários editam o mesmo registro ao mesmo tempo:
- As entidades `Usuario`, `Produto` e `Transacao` possuem o campo `version` anotado com `@Version`
- O JPA incrementa esse campo automaticamente a cada atualização
- Se duas requisições tentarem salvar a mesma versão, a segunda é rejeitada com `409 Conflict`

### 📦 Controle de estoque
Gerencia o estoque dos produtos com segurança e integridade:
- Cada produto possui `quantidadeEstoque` e `estoqueMinimo`
- O estoque nunca pode ficar negativo — trava de segurança no Model
- Quando o estoque atinge o nível crítico (abaixo do mínimo), o sistema gera um alerta automático no log
- As operações de estoque usam `@Transactional` para garantir integridade dos dados

### 💰 Módulo financeiro
Gerencia transações financeiras com precisão e segurança:
- Usa `BigDecimal` para evitar erros de arredondamento em valores monetários
- Controle de status via enum (`PENDENTE`, `CONCLUIDA`, `FALHA`)
- Idempotência por `chaveIdempotencia` — impede pagamentos duplicados por queda de internet ou clique duplo
- `@Transactional` garante que o pagamento é salvo por completo ou não é salvo

### ⚠️ Tratamento global de erros
A classe `GlobalExceptionHandler` centraliza o tratamento de exceções da API:
- `IllegalArgumentException` → `400 Bad Request` com mensagem descritiva
- `ObjectOptimisticLockingFailureException` → `409 Conflict` para conflitos de concorrência

---

## ▶️ Como rodar localmente

**Pré-requisitos:** Java 21 e Maven instalados.

1. Clone o repositório:
```bash
git clone https://github.com/TatianaModelGBock/api-usuarios.git
```

2. Acesse a pasta do projeto:
```bash
cd api-usuarios/api-usuarios
```

3. Rode a aplicação:
```bash
./mvnw spring-boot:run
```

4. A API estará disponível em:
```
http://localhost:8080
```

5. Console do banco H2 disponível em:
```
http://localhost:8080/h2-console
```
> Configurações de acesso ao H2:
> - **JDBC URL:** `jdbc:h2:mem:testdb`
> - **Username:** `sa`
> - **Password:** *(deixar vazio)*

> 💡 Os dados são armazenados em memória — ao reiniciar a aplicação, os dados são resetados.

---

## 📝 Exemplo de fluxo completo

```
1. POST /produtos                        → cria um produto com estoque
2. GET  /produtos                        → lista produtos e anota o ID
3. POST /caixa/vendas                    → inicia uma venda
4. POST /caixa/vendas/1/itens            → adiciona o produto à venda
5. GET  /caixa/vendas/1                  → consulta o total da venda
6. POST /caixa/vendas/1/fechar           → fecha a venda
7. PATCH /produtos/1/venda?quantidade=2  → baixa o estoque
8. POST /api/financeiro/pagar            → registra o pagamento
9. PUT  /api/financeiro/confirmar/1      → confirma o pagamento
```

---

## 📁 Estrutura do projeto

```
src/main/java/com/tatiana/api_usuarios/
├── controller/
│   ├── CaixaController.java
│   ├── ProdutoController.java
│   ├── TransacaoController.java
│   ├── UsuarioController.java
│   └── GlobalExceptionHandler.java
├── dto/
│   ├── AdicionarItemDTO.java
│   ├── FecharVendaDTO.java
│   ├── ProdutoDTO.java
│   ├── TransacaoDTO.java
│   └── UsuarioDTO.java
├── model/
│   ├── ItemVenda.java
│   ├── Produto.java
│   ├── StatusTransacao.java
│   ├── Transacao.java
│   ├── Usuario.java
│   └── Venda.java
├── repository/
│   ├── ProdutoRepository.java
│   ├── TransacaoRepository.java
│   ├── UsuarioRepository.java
│   └── VendaRepository.java
└── service/
    ├── CaixaService.java
    ├── ProdutoService.java
    ├── TransacaoService.java
    └── UsuarioService.java
```

---

