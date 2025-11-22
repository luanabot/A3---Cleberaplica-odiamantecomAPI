# 💎 Estoque Diamante

Sistema de controle de estoque para o armazém **Diamante**, desenvolvido com **Spring Boot**, **Spring Data JPA**, **MySQL** e **Swagger**.

---

## 🛠 Tecnologias

- Java 21  
- Spring Boot 3.5  
- Spring Data JPA  
- MySQL  
- Lombok  
- Swagger (OpenAPI)  
- Maven
- XAMPP

---

## ⚙️ Pré-requisitos

- Java 21 
- Maven 3.8
- MySQL 8
- IDE (IntelliJ IDEA, Eclipse ou VS Code)  

---

Configure o banco de dados no arquivo application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/estoque_diamante  
spring.datasource.username=root  
spring.datasource.password=123456  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect


Crie o banco de dados no MySQL:

CREATE DATABASE estoque_diamante;

▶️ Executando a aplicação

Run: EstoqueDiamanteApplication.java

A aplicação será iniciada em http://localhost:8080

📌 Testando a API com Swagger

O Swagger UI permite testar todos os endpoints da API visualmente:

http://localhost:8080/swagger-ui/index.html

📌 Endpoints principais
Método	Endpoint	Descrição
POST	/api/produtos	Cadastrar um produto  
GET	/api/produtos	Listar todos os produtos  
GET	/api/produtos/{id}	Buscar produto por ID  
PUT	/api/produtos/{id}	Atualizar produto  
DELETE	/api/produtos/{id}	Deletar produto  
PATCH	/api/produtos/{id}/baixa	Dar baixa no estoque  
PATCH	/api/produtos/{id}/repor	Repor estoque  
GET	/api/movimentacoes	Listar todas as movimentações  
GET	/api/movimentacoes/produto/{id}	Listar movimentações de um produto  

📦 Exemplo de JSON para cadastro de produto
{
  "codigo": "1234",
  "nome": "Produto A",
  "descricao": "Descrição do produto",
  "caracteristicas": "Características do produto",
  "quantidadeEstoque": 10,
  "estoqueMinimo": 3
}

📉 Exemplo de JSON para dar baixa no estoque
{
  "quantidade": 1,
  "responsavel": "Luana"
}

💾 Banco de dados

Tabela produto
Coluna	Tipo
id	BIGINT (PK)
descricao	VARCHAR
caracteristicas	VARCHAR
estoque_minimo	INT
quantidade_estoque	INT
data_atualizacao	TIMESTAMP
data_cadastro	TIMESTAMP
Tabela movimentacao
Coluna	Tipo
id	BIGINT (PK)
produto_id	BIGINT (FK)
tipo	ENUM (ENTRADA, SAIDA)
quantidade	INT
responsavel	VARCHAR
data_movimentacao	TIMESTAMP

✅ Funcionalidades

Cadastro, atualização e exclusão de produtos

Controle de estoque (entrada e saída)

Consulta de estoque baixo

Registro de movimentações (entrada e saída)

API documentada via Swagger

📌 Observações

Utilize o Swagger para testar todos os endpoints de forma visual.

O campo responsavel nas operações de estoque deve conter o nome do usuário responsável pela movimentação.
