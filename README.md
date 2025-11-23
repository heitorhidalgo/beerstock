# 🍺 BeerStock API - TDD com Spring Boot

Projeto desenvolvido durante o **Bootcamp da Digital Innovation One (DIO)**.
O objetivo principal deste projeto foi o desenvolvimento de uma **API REST** para gerenciamento de estoques de cerveja, com foco prático na metodologia **TDD (Test Driven Development)** e na escrita de **Testes Unitários** robustos.

---

## 🎯 Objetivos do Projeto

- Desenvolver uma API REST completa com Spring Boot.
- Praticar o ciclo **TDD (Red-Green-Refactor)** para implementação de regras de negócio.
- Escrever testes unitários utilizando **JUnit 5** e **Mockito**.
- Gerenciar massa de dados e conversão de objetos com **MapStruct**.
- Implementar regras de validação de estoque (capacidade máxima e estoque insuficiente).

---

## 🛠 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Web**
- **H2 Database** (Banco em memória)
- **Lombok** (Redução de boilerplate)
- **MapStruct** (Conversão Entity <-> DTO)
- **JUnit 5 & Mockito** (Testes)
- **Maven**

---

## 🧪 A Prática do TDD

**O desenvolvimento seguiu rigorosamente o ciclo do TDD:**

1.  🔴 **RED (Vermelho):** Escrita de um teste unitário que falha inicialmente (pois a funcionalidade ainda não existe).
2.  🟢 **GREEN (Verde):** Implementação do código mínimo necessário para fazer o teste passar.
3.  🔵 **REFACTOR (Refatoração):** Melhoria do código escrito, mantendo os testes passando.

**Funcionalidades desenvolvidas com TDD:**

- **Incrementar Estoque:** Validação de existência da cerveja e limite máximo de estoque.
- **Decrementar Estoque:** Validação de existência e limite mínimo (não permitir estoque negativo).

---

## 🚀 Como Executar o Projeto

1.  **Clone o repositório:**

    ```bash
    git clone [https://github.com/heitorhidalgo/beerstock.git](https://github.com/heitorhidalgo/beerstock.git)
    cd beerstock
    ```

2.  **Execute a aplicação:**

    ```bash
    mvn spring-boot:run
    ```

    A API estará disponível em: `http://localhost:8080/api/v1/beers`

3.  **Rodar os Testes:**
    Para verificar a cobertura de testes e o sucesso das regras de negócio:
    ```bash
    mvn test
    ```

---

## 📡 Endpoints Principais

- `POST /api/v1/beers` - Cadastrar nova cerveja.
- `GET /api/v1/beers/{name}` - Buscar cerveja por nome.
- `GET /api/v1/beers` - Listar todas as cervejas.
- `DELETE /api/v1/beers/{id}` - Deletar cerveja.
- `PATCH /api/v1/beers/{id}/increment` - Incrementar estoque.
- `PATCH /api/v1/beers/{id}/decrement` - Decrementar estoque.

---

## 👤 Autor

**Heitor Hidalgo**

- **GitHub:** [heitorhidalgo](https://github.com/heitorhidalgo)
- **LinkedIn:** [Heitor Hidalgo](https://www.linkedin.com/in/heitorhidalgo)

---

## 📄 Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
