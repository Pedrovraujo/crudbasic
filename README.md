#  FJ ARAÚJO - Sistema de Marcação de Pacientes

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.1-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Frontend](https://img.shields.io/badge/Frontend-HTML%2FCSS%2FJS-lightgrey)
![JWT](https://img.shields.io/badge/Security-JWT%20Auth-yellow)
![CRUD](https://img.shields.io/badge/CRUD-API-success)

---

##  Sobre o Projeto

O **FJ ARAÚJO - Sistema de Marcação de Pacientes** é uma aplicação web completa de **agendamento odontológico**.

O sistema permite:

-  Cadastrar pacientes com nome, idade, serviço e data de atendimento
-  Selecionar serviços com descrições específicas
-  Consultar pacientes por data
-  Editar e excluir cadastros
-  Autenticar o acesso via **JWT (Spring Security)**
-  Definir status de atendimento (**PENDENTE**, **CONCLUÍDO**, **CANCELADO**)

O **frontend** consome uma **API REST** desenvolvida em **Spring Boot**, estruturada em camadas seguindo boas práticas de desenvolvimento.

---

##  Tecnologias Utilizadas

| Camada | Ferramentas |
|--------|-------------|
|  Backend | Java 21, Spring Boot 4.0.1 |
|  Banco de Dados | MySQL 8.x |
|  ORM | Spring Data JPA + Hibernate |
|  Utilitários | Lombok, ModelMapper |
|  Segurança | Spring Security + JWT |
|  Frontend | HTML, CSS, JavaScript |
|  Validação | Bean Validation (Jakarta Validation) |

---

##  Estrutura do Projeto

```bash
src/
 ├── main/
 │   ├── java/com/example/demo/
 │   │   ├── config/              # Configurações da aplicação
 │   │   ├── controller/          # Endpoints REST
 │   │   ├── dto/                 # Objetos de transferência (DTOs)
 │   │   ├── modelo/              # Entidades JPA e Enums
 │   │   ├── repository/          # Interfaces JPA
 │   │   ├── security/            # JWT e configuração de segurança
 │   │   ├── service/             # Lógica de negócio
 │   │   └── valida/              # Tratamento de erros globais
 │   └── resources/
         ├── static/              # Frontend (HTML, CSS, JS)
         └── application.properties
 Banco de Dados
O sistema utiliza MySQL e cria o banco automaticamente se não existir.

 Configuração (application.properties)
spring.application.name=crud-basica
spring.jpa.hibernate.ddl-auto=update

spring.datasource.url=jdbc:mysql://localhost:3306/meubanco?createDatabaseIfNotExist=true
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.show-sql=true
 Variáveis de Ambiente
Crie um arquivo .env ou configure no sistema:

DB_USER=seu_usuario_mysql
DB_PASS=sua_senha_mysql
 Como Executar
 Pré-requisitos
 Java 21+

 Maven 3.9+

 MySQL rodando

 Passos
# Clonar o repositório
git clone https://github.com/Pedrovraujo/crudbasic.git

# Acessar o diretório
cd crudbasic

# Rodar a aplicação
mvn spring-boot:run
 O backend iniciará em: http://localhost:8080
 O frontend pode ser aberto diretamente no arquivo index.html (em src/main/resources/static).

 Autenticação (JWT)
O sistema utiliza autenticação simples com usuário fixo:


"Credenciais inválidas"
O token JWT deve ser enviado nos headers das requisições protegidas:

Authorization: Bearer <seu_token_aqui>
 Endpoints da API
 Pessoas (/pessoas)
Método	Caminho	Descrição
POST	/pessoas	Cadastrar pessoa
GET	/pessoas	Listar todas
GET	/pessoas/{id}	Buscar por ID
PUT	/pessoas/{id}	Editar pessoa
DELETE	/pessoas/{id}	Excluir pessoa
GET	/pessoas/data?data=YYYY-MM-DD	Buscar por data de atendimento
Serviços (/servicos)
Método	Caminho	Descrição
POST	/servicos	Cadastrar novo serviço
GET	/servicos	Listar todos os serviços
Status de Agendamento
Cada atendimento possui um status:

Status	Descrição
PENDENTE	Agendamento ativo aguardando atendimento
CONCLUIDO	Atendimento finalizado
CANCELADO	Agendamento cancelado
 Frontend
O frontend foi desenvolvido com HTML, CSS e JavaScript puro, consumindo a API REST.

Funcionalidades:

 Cadastro e edição de pacientes

 Filtro por data

 Exibição do serviço + descrição

 Atualização automática da lista

 Notificações toast de sucesso e erro

 Boas Práticas Aplicadas
 Camadas separadas (Controller → Service → Repository)
 DTOs para entrada/saída
 Bean Validation com mensagens personalizadas
 Tratamento global de exceções (@RestControllerAdvice)
 Uso de ModelMapper para mapeamento automático
 Front desacoplado via fetch()
 Enum de status no modelo
 JWT para autenticação simples

 Dependências Principais
<dependencies>
    <!-- Spring Boot Web e MVC -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webmvc</artifactId>
    </dependency>

    <!-- JPA e validação -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- MySQL -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- ModelMapper -->
    <dependency>
        <groupId>org.modelmapper</groupId>
        <artifactId>modelmapper</artifactId>
        <version>3.1.1</version>
    </dependency>

    <!-- Segurança e JWT -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
👨‍💻 Autor
Pedro Henrique Lima de Araújo
💼 Desenvolvedor Java Backend
📍 Brasil
📧 pedrovraujo@gmail.com
🔗 GitHub: Pedrovraujo