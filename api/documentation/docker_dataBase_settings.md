## Container com banco MySQL:

Com o docker instalado, basta seguir os comandos abaixo:

Baixar a imagem latest do mysql:
~~~bashs
docker pull mysql install
~~~
Criar o container do banco de dados a partir da imagem do mysql baixada acima:
~~~bashs
docker run -e MYSQL_ROOT_PASSWORD=<<YOUR_PASSWORD>>--name <<YOUR_CONTAINER_NAME>> -d -p 3306:3306 mysql
~~~
Insira os parâmetros YOUR_PASSWORD e YOUR_CONTAINER_NAME. A senha do banco de dados é um dado bastante sensível
pode-se criar uma váriavel de ambiente para isso. 
Como este projeto é apenas uma POC, eu irei deixar exposta a senha no *application.properties*


Não faz sentido o container com o banco de dados está rodando enquanto não estamos desenvolvendo, então é importante  
pausar o container, para fazer isso basta usar o código:
~~~bashs
docker stop <<CONTAINER_ID>>
~~~

Quando quiser voltar a usar o banco de dados criado no container basta iniciar o container novamente:
~~~bashs
docker start <<CONTAINER_ID>>
~~~

## Comandos úteis:

Listar todos os containers ativos
~~~bashs
docker container ls
~~~

Listar todos os containers ativos e inativos
~~~bashs
docker container ls -a
~~~
