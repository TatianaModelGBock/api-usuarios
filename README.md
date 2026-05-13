# 🛒 API de Usuários, Produtos e Caixa

API REST desenvolvida em Java com Spring Boot para praticar os conceitos de arquitetura em camadas (Controller, Service, Repository) com DTOs e regras de negócio.

---

## 🚀 Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Web (REST)
- Maven

---

## 🏗️ Arquitetura

O projeto segue o padrão de camadas:

```
Controller → Service → Repository
```

- **Controller** — recebe as requisições HTTP e devolve as respostas
- **DTO** — transporta apenas os dados necessários entre as camadas
- **Service** — contém as regras de negócio
- **Repository** — acessa e gerencia os dados em memória
- **Model** — representa as entidades da aplicação

---

## 📦 Endpoints

### Usuários `/usuarios`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/usuarios` | Cria um novo usuário |
| GET | `/usuarios` | Lista todos os usuários |
| PUT | `/usuarios/{id}` | Atualiza um usuário |
| DELETE | `/usuarios/{id}` | Remove um usuário |

**Body para criar/atualizar:**
```json
{
  "nome": "Tatiana",
  "email": "tatiana@email.com"
}
```

---

### Produtos `/produtos`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/produtos` | Cria um novo produto |
| GET | `/produtos` | Lista todos os produtos |
| PUT | `/produtos/{id}` | Atualiza um produto |
| DELETE | `/produtos/{id}` | Remove um produto |

**Body para criar/atualizar:**
```json
{
  "nome": "Café",
  "preco": 5.0
}
```

---

### Caixa `/caixa`

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/caixa/vendas` | Inicia uma nova venda |
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

---

## ▶️ Como rodar localmente

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
```

2. Acesse a pasta do projeto:
```bash
cd api-usuarios
```

3. Rode a aplicação:
```bash
./mvnw spring-boot:run
```

4. A API estará disponível em:
```
http://localhost:8080
```

> 💡 Os dados são armazenados em memória — ao reiniciar a aplicação, os dados são resetados.

---

## 📝 Exemplo de fluxo de venda

1. Criar um produto — `POST /produtos`
2. Listar produtos e anotar o ID — `GET /produtos`
3. Iniciar uma venda — `POST /caixa/vendas`
4. Adicionar item à venda — `POST /caixa/vendas/1/itens`
5. Fechar a venda — `POST /caixa/vendas/1/fechar`

---

## 👩‍💻 Autora

Feito por **Tatiana** como projeto de estudo de arquitetura REST com Java Spring Boot.
