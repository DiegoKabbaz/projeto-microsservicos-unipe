# Projeto: Microsserviços com Spring Boot e Spring Cloud

Este projeto implementa uma arquitetura de microsserviços para um sistema simples de e-commerce (Produtos e Pedidos), com base nos requisitos de um diagrama de classes.

O ecossistema foi construído utilizando Spring Boot para os serviços e Spring Cloud para gerenciar a comunicação, registro e roteamento.

## Arquitetura

A arquitetura é composta por 4 serviços independentes, cada um em sua própria pasta:

1.  **`discovery-service` (Porta `8761`)**
    * **Tecnologia:** Spring Cloud Netflix Eureka Server.
    * **Responsabilidade:** Atua como o "Serviço de Registro" (Service Registry). Todos os outros microsserviços se registram nele, permitindo que se descubram (Service Discovery) dinamicamente na rede.
    * **Painel:** `http://localhost:8761`

2.  **`api-gateway` (Porta `8080`)**
    * **Tecnologia:** Spring Cloud Gateway.
    * **Responsabilidade:** É o portão de entrada único ("API Gateway") para todo o sistema. Ele recebe todas as requisições externas e as roteia para o microsserviço correto (`produto-service` ou `pedido-service`), com base no caminho da URL (ex: `/produtos` ou `/pedidos`).

3.  **`produto-service` (Porta `8081`)**
    * **Tecnologia:** Spring Boot, Spring Web, Spring Data JPA.
    * **Responsabilidade:** Microsserviço que gerencia o CRUD (Criar, Ler, Atualizar, Deletar) da entidade `Produto`.

4.  **`pedido-service` (Porta `8082`)**
    * **Tecnologia:** Spring Boot, Spring Web, Spring Data JPA.
    * **Responsabilidade:** Microsserviço que gerencia o CRUD da entidade `Pedido` e seu status (CRIADO, CONFIRMADO, CANCELADO).

## Tecnologias Utilizadas

* **Backend:** Java 21, Spring Boot 3.x
* **Microsserviços:** Spring Cloud (Netflix Eureka, Spring Cloud Gateway)
* **Banco de Dados:** H2 Database (Banco em memória para cada serviço)
* **Persistência:** Spring Data JPA (Hibernate)
* **Build:** Apache Maven
* **Utilitários:** Lombok

## Como Executar o Projeto

### Pré-requisitos

* JDK 21 (ou superior)
* Apache Maven 3.8 (ou superior)
* Um cliente de API (como Postman ou Insomnia)

### Ordem de Inicialização

A ordem em que os serviços são iniciados é **crucial** para que a arquitetura funcione. Você deve iniciar cada serviço em um terminal separado.

1.  **1º - `discovery-service` (Servidor Eureka)**
    ```bash
    cd discovery-service
    ./mvnw spring-boot:run
    ```

2.  **2º - `api-gateway` (Gateway)**
    ```bash
    cd api-gateway
    ./mvnw spring-boot:run
    ```

3.  **3º - `produto-service` (API de Produtos)**
    ```bash
    cd produto-service
    ./mvnw spring-boot:run
    ```

4.  **4º - `pedido-service` (API de Pedidos)**
    ```bash
    cd pedido-service
    ./mvnw spring-boot:run
    ```

Após iniciar os 4 serviços, acesse o painel do Eureka (`http://localhost:8761`) para confirmar que `API-GATEWAY`, `PRODUTO-SERVICE` e `PEDIDO-SERVICE` estão registrados com o status `UP`.

## Testando os Endpoints (via Gateway)

Todas as requisições devem ser feitas para a porta do **API Gateway (`http://localhost:8080`)**.

### Serviço de Produto

#### Criar Produto
* `POST` `http://localhost:8080/produtos`
* **Body (JSON):**
    ```json
    {
        "nome": "Teclado Mecânico",
        "quantidade": 10,
        "descricao": "Teclado ABNT2 com switch azul",
        "preco": 250.50
    }
    ```

#### Listar Produtos
* `GET` `http://localhost:8080/produtos`

---

### Serviço de Pedido

#### Criar Pedido
* `POST` `http://localhost:8080/pedidos`
* **Body (JSON):** (Liste os IDs dos produtos que você criou)
    ```json
    {
        "idProdutos": [1] 
    }
    ```
* **Resposta:**
    ```json
    {
        "id": 1,
        "dataPedido": "2025-10-21T23:59:00.000000",
        "status": "CRIADO",
        "idProdutos": [1]
    }
    ```

#### Listar Pedidos
* `GET` `http://localhost:8080/pedidos`

#### Confirmar um Pedido
* `POST` `http://localhost:8080/pedidos/1/confirmar`

#### Cancelar um Pedido
* `POST` `http://localhost:8080/pedidos/1/cancelar`

---

### Autor

* **Diego Altenkirch Kabbaz**
* **Glauber Souza Monteiro**
