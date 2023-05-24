
## Disque Sea Web


### Introdução
Esta aplicação tem o objetivo de fazer o controle do estoque da Disque Sea, uma loja do ramo alimentício que
fornece para os seus clientes uma variedade de produtos, principalmente peixes e frutos do mar. Os produtos 
comercializados possuem uma peculiaridade:

>"A maioria dos produtos vendidos na Disque Sea são congelados e este é um dos motivos da necessidade de uma solução que controle o fluxo de estoque de forma precisa e automatizada."

Diante deste cenário, eu irei desenvolver uma solução que consiga sanar a dor do cliente que é saber a quantidade
de cada produto de forma rápida e confiável. O Disque Sea App é um [ERP](https://www.oracle.com/br/erp/what-is-erp/) que irá auxiliar no controle de estoque.

### Escolha de tecnologias
Irei desenvolver um [MVP (Minimal Viable Product)](https://pt.wikipedia.org/wiki/Produto_vi%C3%A1vel_m%C3%ADnimo) com as seguintes características:
O backend obedece o padrão de arquitetura MVC, ele fornecerá uma REST API para comunicação com o frontend da aplicação.

A Stack utilizada para cosntrução do backend é Java 17, usarei o framework SpringBoot.

O frontend será feito em TypeScript com uso da biblioteca react.

### Funcionalidades
Este aplicativo terá quartro grupos de funcionalidades, estas são ditas a seguir.
Operações

1. Operações
   * Saída: ato de retirar uma quantidade de algum produto do estoque (interfere na carteira)
   * Entrada: ato de inserir uma quantidade de algum produto do estoque (interfere na carteira)
   * Resetar: ato de deletar todas as operações
   
2. Produtos
   * Consulta: ato de consultar um produto do estoque
   * Atualizar: ato de atualizar características de um produto do estoque
   * Adicionar: ato de adicionar um produto novo ao estoque
   * Remover: ato remover produto do estoque

3. Carteira
    * Visualizar: ato de consultar quanto de saldo existe na carteira
    * Resetar: ato de zerar o saldo da carteira.
   
4. Documentos
   * Estoque: ato de gerar PDF contendo todos os produtos e suas características
   * Histórico: ato de gerar PDF contendo o histórico de entradas e saídas
   * Catálogo:  ato de gerar catálogo em PDF de divulgação

### Disque Sea API - Links úteis
* [Documentação da API](./api/documentation/documentation_API.md)
* [Desenho da Arquitetura](./api/documentation/disque_sea_arquitetura.pdf)
* [Desenho do UML](./api/documentation/disque_sea_UML.pdf)
* [Uso de docker para criar um container com banco de dados MySQL](./api/documentation/docker_database_settings.md)

#### Assuntos utilizados durante desenvolvimento do BACKEND

1. Rest API

2. Padrão MVC

3. Desenho de UML

4. Desenho de Arquitetura

5. Documentação de API

6. Tratamento de exceções

7. Criação de anotação para validação customizada (Jakarta Validation: ConstraintValidator)

8. Criação de query nativa (JPA)

9. Uso de Specification (JPA)

10. Criação de DTOs com ModelMapper

11. Validação de dados (Jakarta Validation)

12. Git / Github

13. [Uso do Figma para desenho de arquitetura e UML](https://www.figma.com/file/MvplDvfvWeViwLAub2m6U2/DisqueSea---UML?type=whiteboard&node-id=0%3A1&t=vR7u9DwRb5Oyyp0r-1)