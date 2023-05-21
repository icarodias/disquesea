# Disque Sea API

------
Container com mysql:
Com docker instalado:

Baixar a imagem latest do mysql:
~~~bashs
docker pull mysql install
~~~
Criar container do banco de dados do a imagem latest do mysql:
~~~bashs
docker run -e MYSQL_ROOT_PASSWORD=<<YOUR_PASSWORD>>--name <<YOUR_CONTAINER_NAME>> -d -p 3306:3306 mysql
~~~

Ao terminar de trabalhar na aplicação, lembrar de pausar o container:
~~~bashs
docker stop <<CONTAINER_ID>>
~~~

Quando quiser voltar a usar o banco de dados criado no container basta iniciar o container novamente:
~~~bashs
docker start <<CONTAINER_ID>>
~~~

Comandos úteis:

Listar todos os containers ativos
~~~bashs
docker container ls
~~~

Listar todos os containers ativos e inativos
~~~bashs
docker container ls -a
~~~


-----



Esta api é responsável pelo backend da aplicação DisqueSea

## Order API

### 1. Find all orders

|Endpoint||
|----|---:|
|GET|/orders|

#### **query params**
* isSell
* status
* fromDate
* toDate


#### **response**
~~~json
 [
    {
        "id":1,
        "amount": 1.000,
        "price": 55.00,
        "isSell": true,
        "description":"",
        "date": "02/05/2023",
        "product": {
            "id": 20,
            "name": "Camarão",
            "category": "SHRIMP",
            "scale": "KILOGRAM"
        }
    },
    {
        "id": 2,
        "amount": 10,
        "price": 150.00,
        "isSell": false,
        "description":"Abastecendo estoque",
        "date": "02/05/2023",
        "product": {
            "id": 2,
            "name": "Doce de Leite",
            "category": "MEAT",
            "scale": "UNIT"
        }
    },
 ] 
~~~

### 2. Register an order

Endpoint responsável por cadastar uma venda ou um abastencimento de algum produto do estoque.

- Caso o preço não seja informado no payload e seja uma venda, então o preço será calculado automaticamente pela aplicação (quantidade multiplicado pelo preço do produto)

|Endpoint||
|----|---:|
|POST|/orders|

#### **request**
~~~json
{
    "isSell": true,
    "amount": 5,
    "productId": 2,
    "description": "",
    "price": 75.00
}
~~~

#### **response**
~~~json
{
    "id": 2,
    "amount": 5,
    "price": 75.00,
    "isSell": true,
    "description":"",
    "date": "02/05/2023",
    "product": {
        "id": 2,
        "name": "Doce de Leite",
        "category": "OTHER",
        "scale": "UNIT"
    }
}
~~~


## Product API

### 1. Find all products

|Endpoint||
|----|---:|
|GET|/products|

#### **query params**
* name
* scale
* status
* category
* isVisibleInCatalog

#### **response**
~~~json
 [
    {
        "id": 20,
        "name": "Camarão",
        "amount": 30.000,
        "price": 55.00,
        "isVisibleInCatalog": true,
        "scale": "KILOGRAM",
        "status": "AVAILABLE",
        "category": "SHRIMP"

    },
    {
        "id": 2,
        "name": "Doce de Leite",
        "amount": 3,
        "price": 15.00,
        "isVisibleInCatalog": true,
        "scale": "UNIT",
        "status": "CRITICAL",
        "category": "OTHER"

    },
 ] 
~~~

### 2. Adding product 

|Endpoint||
|----|---:|
|POST|/products|

#### **request**
~~~json
    {
        "name": "Camarão",
        "amount": 30.000,
        "price": 55.00,
        "isVisibleInCatalog": true,
        "scale": "KILOGRAM",
        "category": "SHRIMP"
    }
~~~

### 3. Update product

|Endpoint||
|----|---:|
|PATCH|/products/{id}|

#### **request**
~~~json
{
    "name": "Camarão",
    "price": 55.00,
    "isVisibleInCatalog": true,
    "scale": "KILOGRAM",
    "status": "AVAILABLE",
    "category": "SHRIMP"
}
~~~

#### **response**
~~~json
 [
    {
        "id": 20,
        "name": "Camarão",
        "amount": 30.000,
        "price": 55.00,
        "isVisibleInCatalog": true,
        "scale": "KILOGRAM",
        "status": "AVAILABLE",
        "category": "SHRIMP"

    },
    {
        "id": 2,
        "name": "Doce de Leite",
        "amount": 3,
        "price": 15.00,
        "isVisibleInCatalog": true,
        "scale": "UNIT",
        "status": "CRITICAL",
        "category": "OTHER"

    },
 ] 
~~~

### 3. Delete product

|Endpoint||
|----|---:|
|DELETE|/products/{id}|


## Wallet API

### 1. Get Wallet Value

|Endpoint||
|----|---:|
|GET|/wallet|

#### **response**
~~~json
{
    "value": 1200.40
}
~~~

### 2. Reset Wallet

|Endpoint||
|----|---:|
|PUT|/wallet|

## Document API


### 1. Generate storage document

Endpoint responsável por gerar PDF contendo todos os produtos e suas características

|Endpoint||
|----|---:|
|GET|/products/download|


### 2. Generate catalog document

Endpoint responsável por gerar catálogo em PDF de divulgação

|Endpoint||
|----|---:|
|GET|/products/catalog|

### 3. Generate order history document

Endpoint responsável por gerar PDF contendo o histórico de entradas e saídas

|Endpoint||
|----|---:|
|GET|/orders/download|
