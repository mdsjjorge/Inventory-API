# Spring Boot - CRUD Inventario

Este repositório contém uma API CRUD Spring Boot de inventário de itens.

## Clonando o Projeto

Para clonar este projeto para o seu computador:

 - Abra o terminal ou a linha de comando. <br>
 - Navegue para a pasta onde você deseja armazenar o projeto. Execute:

```
git clone https://github.com/mdsjjorge/Inventory-API.git
```
## Configurando o Projeto Localmente
Configurando localmente:
 - Abra o projeto em sua IDE ( IntelliJ IDEA, Eclipse, etc. ). <br>
 - Certifique-se de que você tenha o Java Development Kit (JDK) instalado. <br>
 - No arquivo src/main/resources/application.properties, você pode configurar as informações do banco de dados PostgreSQL. <br>
 - Certifique-se de definir a URL, nome de usuário e senha corretos:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/inventario
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## Executando o Aplicativo
 - Abra o projeto em sua IDE. <br>
 - Localize a classe InventoryApplication.java e execute-a. <br>
 - O aplicativo será iniciado e estará acessível em http://localhost:8080.

## Endpoints da API
 - GET /inventory 							- Retorna todos os itens do inventário. <br>
 - GET /inventory/:id 						- Retorna detalhes de um item por ID. <br>
 - GET /inventory/howmany 					- Retorna a quantidade total de itens. <br>
 - GET /inventory/howmany?complete=true 	- Retorna a quantidade de itens complete true. <br>
 - GET /inventory/howmany?complete=false 	- Retorna a quantidade de itens complete false. <br>
 - GET /inventory/howmany?complete=all 		- Retorna a quantidade total de itens cadastrados. <br>
 - POST /inventory 							- Cadastra um novo item. <br>
 - POST /inventory/createList 				- Cadastra uma lista de itens. <br>
 - PUT /inventory/:id 						- Altera os dados de um determinado item. <br>
 - DELETE /inventory/:id 					- Exclui um item. <br>
 
 Exemplo de consulta utilizando Insomnia ou Postman: [metodo GET]
 
 ```
 	localhost:8080/inventory
 ```
 
## Documentacao Swagger
Para ver a documentacao Swagger da API, acessar:

```
 http://localhost:8080/swagger-ui/index.html
```
 
## Contribuindo
Se você quiser contribuir para este projeto, fique à vontade para enviar pull requests ou relatar problemas.
 
 