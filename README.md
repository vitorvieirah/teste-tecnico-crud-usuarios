# TESTE TECNICO | CRUD DE USUÁRIOS

## SOBRE
Projeto referente ao teste técnico da empresa Powerkr, que consiste em um CRUD (create, read, update e delete) de usuários com fins de testar conhecimentos técnicos nas seguintes tecnologias:

- Java
- API Rest
- Spring Boot
- Spring Security
- JWT
- Criptografia de senhas (BCrypt)
- Banco de dados H2
- Swagger

## TECNOLOGIAS USADAS
Além das tecnologias requisitadas, foram utilizadas também:

- Lombok
- Spring Boot Validation
- Jackson

## COMO UTILIZAR

## 1° Subir a aplicação localmente

Para utilizar o projeto siga os seguintes passos:

### 1° Clonar o projeto na sua máquina
Abra seu terminal e no seu diretório de escolha rode o seguinte comando:
```
git clone https://github.com/vitorvieirah/teste-tecnico-crud-usuarios
``` 

Após isso entre no diretório que será criado com o seguinte nome `teste-tecnico-crud-usuarios`.

### 2° Baixar, atualizar e buildar o projeto com o gerenciador de dependências

### ATENÇÃO ⚠️
Para essa etapa é essêncial que o gerenciador de dependências Maven esteje baixado e configurado corretamente.

Também a JDK na versão 17 que foi a versão utilizado no projeto.

Para baixar, atualizar e buildar o projeto, primeiro certifique-se que esteja no diretório raiz do projeto, `teste-tecnico-crud-usuarios`, e então execute o seguinte comando:
```
mvn clean install
```

### 3° Executar a aplicação
E por fim para executar a aplicação basta entrar no diretório onde está o `.jar` da aplicação `\teste-tecnico-crud-usuarios\target` e executar o seguinte comando:
```
java -jar usuario-crud-teste.jar   
```

E pronto sua aplicação estará em execução.

---

## 2° Mandar requisições para a API

Existem duas opções para fazer requisições:

### 1° Usando Swagger
Após subir a aplicação, acesse o seguinte link no seu navegador: `http://localhost:8080/swagger-ui.html` para mandar requisições para a API por meio do Swagger.

### 2° Usando Postman Collection
Após subir a aplicação, na raiz do projeto terá um arquivo chamado  `\teste-tecnico-crud-usuarios\teste-tecnico-usuario.postman_collection`, importe esse arquivo no Postman e faça suas requisições.

## BANCO DE DADOS DA APLICAÇÃO 
O projeto utiliza o banco de dados em memória H2, para acessar basta entrar no seguinte link em seu navegador `http://localhost:8080/h2` e entrar como usuário `admin` e senha `123`.



