# TESTE TÉCNICO | CRUD DE USUÁRIOS

## SOBRE
Este projeto refere-se ao teste técnico da empresa Powerkr, que consiste em um CRUD (Create, Read, Update e Delete) de usuários, com o objetivo de testar conhecimentos técnicos nas seguintes tecnologias:

- Java
- API Rest
- Spring Boot
- Spring Security
- JWT
- Criptografia de senhas (BCrypt)
- Banco de dados H2
- Swagger

## TECNOLOGIAS USADAS
Além das tecnologias requisitadas, também foram utilizadas:

- Lombok
- Spring Boot Validation
- Jackson

## COMO UTILIZAR

## 1° Subir a aplicação localmente

Para utilizar o projeto, siga os passos a seguir:

### 1° Clonar o projeto na sua máquina
Abra seu terminal e, no diretório de sua escolha, rode o seguinte comando:
```
git clone https://github.com/vitorvieirah/teste-tecnico-crud-usuarios
```

Após isso, entre no diretório que será criado com o nome `teste-tecnico-crud-usuarios`.

### 2° Baixar, atualizar e buildar o projeto com o gerenciador de dependências

### ATENÇÃO ⚠️
Para esta etapa, é essencial que o gerenciador de dependências Maven esteja baixado e configurado corretamente.

Além disso, a JDK na versão 17 deve estar instalada, pois foi a versão utilizada no projeto.

Para baixar, atualizar e buildar o projeto, primeiro certifique-se de que você esteja no diretório raiz do projeto, `teste-tecnico-crud-usuarios`, e então execute o seguinte comando:
```
mvn clean install
```

### 3° Executar a aplicação
Por fim, para executar a aplicação, basta entrar no diretório onde está o arquivo `.jar` da aplicação `\teste-tecnico-crud-usuarios\target` e executar o seguinte comando:
```
java -jar usuario-crud.jar   
```

E pronto! Sua aplicação estará em execução.

---

## 2° Fazer requisições para a API

Existem duas opções para realizar requisições:

### 1° Usando Swagger
Após subir a aplicação, acesse o seguinte link em seu navegador: `http://localhost:8080/swagger-ui.html` para enviar requisições à API por meio do Swagger.

### 2° Usando Postman Collection
Após subir a aplicação, na raiz do projeto, haverá um arquivo na pasta `arquivos` chamado `teste-tecnico-usuario.postman_collection`, caminho: `\teste-tecnico-crud-usuarios\arquivos\teste-tecnico-usuario.postman_collection`. 

Importe esse arquivo no Postman e faça suas requisições.

## BANCO DE DADOS DA APLICAÇÃO 
O projeto utiliza o banco de dados em memória H2. Para acessá-lo, basta entrar no seguinte link em seu navegador: `http://localhost:8080/h2` e usar as credenciais de usuário `admin` e senha `123`.





