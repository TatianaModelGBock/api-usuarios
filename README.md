# 🛒 API de Usuários, Produtos e Caixa

API REST desenvolvida em Java com Spring Boot como projeto de estudo de arquitetura em camadas (Controller, Service, Repository), DTOs, persistência com JPA, ordenação, idempotência e controle de concorrência.

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

**Body para criar/atualizar:**
```json
{
  "nome": "Café",
  "preco": 5.0
}
```

**Regras:**
- Nome deve ser único — não é possível cadastrar dois produtos com o mesmo nome
- Preço deve ser maior que zero
- Retorna `400 Bad Request` se o nome já estiver cadastrado ou o preço for inválido

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

## ✅ Funcionalidades implementadas

### 🔤 Ordenação com Comparator
Os resultados dos endpoints `GET` são ordenados automaticamente:
- Usuários — ordem alfabética por nome (A→Z)
- Produtos — ordem crescente por preço (menor→maior)

### 🔒 Idempotência
Garante que a mesma operação não cause efeitos duplicados:
- Cadastro de usuário valida se o email já existe antes de criar
- Cadastro de produto valida se o nome já existe antes de criar
- Impede que dados duplicados sejam inseridos mesmo que a requisição seja enviada mais de uma vez

### ⚡ Controle de concorrência com @Version
Protege contra o problema de "atualização perdida" quando dois usuários editam o mesmo registro ao mesmo tempo:
- As entidades `Usuario` e `Produto` possuem o campo `version` anotado com `@Version`
- O JPA incrementa esse campo automaticamente a cada atualização
- Se duas requisições tentarem salvar a mesma versão, a segunda é rejeitada com `409 Conflict` e a mensagem: `"Registro alterado por outro usuário. Tente novamente."`

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

## 📝 Exemplo de fluxo completo de venda

```
1. POST /produtos          → cria um produto
2. GET  /produtos          → lista produtos e anota o ID
3. POST /caixa/vendas      → inicia uma venda (retorna o ID da venda)
4. POST /caixa/vendas/1/itens  → adiciona o produto à venda
5. GET  /caixa/vendas/1    → consulta o total da venda
6. POST /caixa/vendas/1/fechar → fecha a venda informando o valor recebido
```

---

## 📁 Estrutura do projeto

```
src/main/java/com/tatiana/api_usuarios/
├── controller/
│   ├── CaixaController.java
│   ├── ProdutoController.java
│   ├── UsuarioController.java
│   └── GlobalExceptionHandler.java
├── dto/
│   ├── AdicionarItemDTO.java
│   ├── FecharVendaDTO.java
│   ├── ProdutoDTO.java
│   └── UsuarioDTO.java
├── model/
│   ├── ItemVenda.java
│   ├── Produto.java
│   ├── Usuario.java
│   └── Venda.java
├── repository/
│   ├── ProdutoRepository.java
│   ├── UsuarioRepository.java
│   └── VendaRepository.java
└── service/
    ├── CaixaService.java
    ├── ProdutoService.java
    └── UsuarioService.java
```

---

## 👩‍💻 Autora

Feito por **Tatiana** como projeto de estudo de arquitetura REST com Java Spring Boot.
