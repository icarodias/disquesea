# Disque Sea API

------

Esta api é responsável pelo backend da aplicação DisqueSea

## Order API

### 1. Find all orders

|Endpoint||
|----|---:|
|GET|/orders|

#### **query params**
* isSell
* fromDate
* toDate
* productId
* productCategory


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
    }
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

### 3. Reset Orders

Endpoint responsável por deletar todas os pedidos realizados.

|Endpoint||
|----|---:|
|DELETE|/orders|


## Product API

### 1. Find all products

Endpoint responsável por buscar todos os produtos.
* Os filtros são somados, ou seja, caso seja inserido filtro de  _scale_ e _name_ então vai buscar pelos producos que contenham essas duas propriedas juntas.


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
    }
 ] 
~~~

### 2. Adding product 

Endpoint responsável por adicionar um produto ao estoque.

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
    }
 ]
~~~

### 3. Update product

Endpoint responsável por atualizar produto.

|Endpoint||
|----|---:|
|PUT|/products/{id}|

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
    }
 ]
~~~

### 3. Delete product

Endpoint responsável por deletar um produto. 

* Produto só pode ser deletado se não existir nenhuma ordem que tenha utilizado ele.

|Endpoint||
|----|---:|
|DELETE|/products/{id}|


## Wallet API

### 1. Get Wallet Value

Endpoint responsável por obter o valor da carteira.

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

Endpoint responsável por zerar a carteira.

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
