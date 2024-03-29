# ![uit](https://user-images.githubusercontent.com/62891985/82739494-5e96bd80-9d16-11ea-993d-25c048bad462.png) Projeto Itaú/USCS - Conta Corrente

## About
- Projeto para simular um sistema bancario, incluindo serviços como transacoes de debito e credito, criacao de contas de usuarios... Usamos Java para implemetacao dos serviços, kafka para trafego de mensagens e cassandra como banco de dados.

- Project to simulate a banking system, including services such as debit and credit transactions, creation of user accounts... We use Java for service implementation, kafka for message traffic and cassandra as database.

## Tech Stack

| Dependency             | Version |
| -----------------------|---------|
| 'Java'.                | '11'    |
| 'Spring Boot'          | '2.4.5' |
| 'Junit'                | '5.6.0' |
| 'Telegram Bot'         | '5.2.0' |
| 'WebFlux'              | '2.4.5' |
| 'SpringData Cassandra' | '2.4.5' |

- Banco de dados
  - Cassandra
 
- Docker
  - Cassandra
  - Kafka
  - Zookeeper

## Etapas para a instalação

**1. Clonar o repositório**

```bash
https://github.com/Puglielli/contacorrente.git
```

**2. Instalar o Docker**

Site para download do [Docker](https://docs.docker.com/get-docker/).

**3. Instalar e configurar o banco de dados *Cassandra***

  **3.1. Fazer o pull do repositório da *Cassandra***

  `docker pull datastax/dse-server:5.1.18`

  **3.2. Para criar o container da *Cassandra*, execute o comando:**
  
  `docker run -e DS_LICENSE=accept --memory 4g --name cassandra -p 9042:9042 -d datastax/dse-server:5.1.18`

  **3.3. Para fazer a copia do arquivo *cassandra.yaml* para dentro do container, execute o comando:**
 
  `docker cp <FILE_CASSANDRA> cassandra:/opt/dse/resources/cassandra/conf/`

  Obs.: Substituir o ***<FILE_CASSANDRA>*** pelo diretório do arquivo *cassandra.yaml*, que está localizado no repositório do projeto `"_/contacorrente/src/main/resources/config/cassandra.yaml_"`.

  **3.4. Realizar stop e start do container *Cassandra***
  
  `docker stop cassandra`

  `docker start cassandra`

  **3.5. Para configurar e criar as tabelas, execute o comando:**
  
  `docker exec -it cassandra bash`

  **3.5.1. Para logar na *Cassandra*, execute o comando:**
  
  `cqlsh -u cassandra -p cassandra`

  **3.5.2. Criar o Usuário**

  `CREATE ROLE root with SUPERUSER = true AND LOGIN = true and PASSWORD = 'root';`

  **3.5.3. Criar o Keyspace**

  `CREATE KEYSPACE dbo WITH REPLICATION = {'class': 'SimpleStrategy','replication_factor' : 1};`

  `USE dbo;`

  **3.5.1. Criar as tabelas**

```bash
  CREATE TABLE cliente (
      nome VARCHAR,
      cpf_cnpj VARCHAR PRIMARY KEY,
      tipo_de_cliente VARCHAR,
      endereco VARCHAR,
      profissao VARCHAR,
      razao_social VARCHAR,
      inscr_estadual VARCHAR,
      num_conta VARCHAR,
      ativo INT
  );
```
```bash
  CREATE TABLE conta (
      num_conta VARCHAR PRIMARY KEY,
      agencia VARCHAR,
      dac INT,
      saldo DOUBLE,
      ativo INT
  );
```
```bash
  CREATE TABLE historico (
      id UUID PRIMARY KEY,
      num_conta VARCHAR,
      tipo_de_transacao VARCHAR,
      tipo_de_operacao VARCHAR,
      data timestamp,
      status INT
  );
```

**4. Instalar e configurar o *Kafka* e *Zookeeper***

  **4.1. Fazer o clone do repositório que contem o *Kafka* e *Zookeeper***

  `git clone https://github.com/confluentinc/cp-docker-images`

  **4.2. Após clonado, navegue até a pasta cp-docker-images/examples/kafka-single-node e execute o comando:**
  
  `docker-compose up -d`

  **4.3. Para listar os serviços Kafka e Zookeeper, execute o comando:**
  
  `docker-compose ps`

  **4.4. Para criar um Topic no Kafka, execute o comando:**

```bash
  docker-compose exec kafka  \
  kafka-topics --create --topic bank-listener --partitions 3 --replication-factor 1 --if-not-exists --zookeeper zookeeper:2181
```
    
  **4.5. Para validar se o Topic foi criado, execute o comando:**

```bash
  docker-compose exec kafka  \
  kafka-topics --describe --topic bank-listener  --zookeeper zookeeper:2181
```

**5. importar o projeto no IDE**

Executar a classe `ContacorrenteApplication`.

## Rotas

**Cliente**

```bash
  GET       http://localhost:8081/cliente
  GET       http://localhost:8081/cliente/{cpf_cnpj}
  POST      http://localhost:8081/cliente
  PUT       http://localhost:8081/cliente/{cpf_cnpj}
  DELETE    http://localhost:8081/cliente/{cpf_cnpj}
```

**Body (POST)**

```bash
  {
    "nome": "Nome",
    "cpf_cnpj": "11111111111",
    "endereco": "Rua Teste, 404",
    "profissao": "Tester I",
    "razao_social": "",
    "inscr_estadual": ""
  }
```

**Body (PUT)**

```bash
  {
    "nome": "Nome tester",
    "endereco": "Rua Teste 3, 320",
    "profissao": "Tester II",
    "razao_social": "Teste LTDA",
    "inscr_estadual": "200404401500"
  }
```

**Conta Corrente**

```bash
  GET     http://localhost:8081/conta-corrente
  GET   http://localhost:8081/conta-corrente/{num_conta}
  PUT   http://localhost:8081/conta-corrente/debito
  PUT   http://localhost:8081/conta-corrente/credito
```

**Body (PUT)**

```bash
  {
    "num_conta": "123456-7",
    "debito": 50
  }
```

```bash
  {
    "num_conta": "123456-7",
    "credito": 50
  }
```

**Historico**

```bash
  GET     http://localhost:8081/historico
  GET     http://localhost:8081/historico/id/{id}
  GET     http://localhost:8081/historico/num-conta/{num_conta}
```
