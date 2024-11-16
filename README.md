# Sportify Application
Está aplicação é feita em Spring Boot utilizando Java 20. Para persistência de dados foi utilizado MySQL e o build e deploy da aplicação é feito em containers utilizando Docker.

---

### **Informações utéis e premissas**
Para fazer o deploy é preciso ter [Docker](https://www.docker.com/get-started) instalado na máquina e para melhor visualização dos containers da aplicação e banco de dados é aconselhado instalar o Docker Desktop.
  
No repositório acompanha o Dockerfile com as etapas de build e deploy da aplicação, o mesmo é utilizado no compose. O compose orquestra o deploy da aplicação subindo um banco de dados MySQL em sua ultima versão e a aplicação utilizando a JRE 20, ambos em containers apartados.

Se quiser durante os testes acompanhar como está o banco de dados no seu Workbeanch por exemplo faça a conexão com o banco de dados com os dados abaixo (os mesmo usados pela aplicação):

- Hostname: 127.0.0.1
- Port: 3306
- Username: su
- Password: pass

---

## **Como rodar a aplicação**

#### 1. Clone o repositório e acesse o projeto

```bash
git clone https://github.com/Icarolmo/Sportify.git
cd Sportify
```

#### 2. Execute compose para subir a aplicação

```bash
docker-compose up --build -d 
```

#### 3. Após a etapa de build e deploy verifique os containers de pé

```bash
docker ps
```

Observação: a aplicação irá subir no host  `http://localhost:8080`

---

## **APIs e exemplos**
Segue exemplo de requisições e APIs para utilizar. Todas são para o host da aplicação acima e segue os exemplos dos métodos HTTP que devem ser enviados e o endereço da API. Toda requisição que é preciso de corpo é enviado conteúdo no formato JSON como seguem nos exemplos.
### APIs abertas que não necessitam de autenticação
#### 1. Criar um usuário.

**[POST]** `/auth/register`

Exemplo de corpo d requisição:
```json
{
	"first_name":"Icaro",
	"last_name":"Oliveira",
	"email":"icarolmo@usp.br",
	"password":"icaro123",
	"role":"COMMON"
}
```

#### 2. Login e authenticação.

**[POST]** `/auth/login`

Exemplo de corpo da requisição:
```json
{
	"email":"icarolmo@usp.br",
	"password":"icaro123"
}
```

Exemplo de corpo de resposta:
```json
{
	"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6ImljYXJvbG1vQHVzcC5iciIsImV4cCI6MTczMTgxMTMzOH0.vqmLGOEjpWtYjBjxhdKmEOV2XCQfI6oTFfKp9NCPLao"
}
```

### APIs fechadas que necessitam de autenticação (token)
#### 1. Criar um evento.

**[POST]** `/event/create`

Exemplo de corpo d requisição:
```json
{
	"activity_title":"futebol na quarta feira",
	"type":"FUTEBOL",
	"description":"um fute a noite com umas pessoas",
	"localization":"QUADRA-INTERNA-1",
	"date":"20-11-2024",
	"start_hour":"14:00:00",
	"end_hour":"16:00:00",
	"max_subscribes":"10"
}
```

#### 2. Atualizar um evento.

**[POST]** `/event/update`

Exemplo de corpo da requisição:
```json
{
	"activity_title":"futebol na quarta feira",
	"type":"FUTEBOL",
	"description":"um fute a noite com umas pessoas que eu conheço",
	"localization":"QUADRA-INTERNA-2",
	"date":"23-11-2024",
	"start_hour":"14:00:00",
	"end_hour":"16:00:00",
	"max_subscribes":"5"
}
```

#### 3. Se inscriver em um evento.

**[POST]** `/event/subscribe/{activity_title}`

Exemplo de requisição: `/event/subscribe/futebol na quarta feira`

Corpo de reposta deve vir vazio apenas com status 200.
