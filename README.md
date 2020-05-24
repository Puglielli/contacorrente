# ![uit](https://user-images.githubusercontent.com/62891985/82739494-5e96bd80-9d16-11ea-993d-25c048bad462.png) Projeto Itaú/USCS - Conta Corrente

Tecnologias utilizadas:

- SpringBoot
  - Web
  - DevTools
  - Lombok
  - SpringData Cassandra
  - Spring Kafka

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
  CREATE TABLE clienteVO (
      nome VARCHAR,
      cpf_cnpj VARCHAR PRIMARY KEY,
      tipo_de_cliente VARCHAR,
      endereco VARCHAR,
      profissao VARCHAR,
      razao_social VARCHAR,
      incr_estadual VARCHAR,
      num_conta VARCHAR
  );
```
```bash
  CREATE TABLE conta (
      num_conta VARCHAR PRIMARY KEY,
      agencia VARCHAR,
      dac INT,
      saldo DOUBLE
  );
```
```bash
  CREATE TABLE historico (
      cpf_cnpj VARCHAR PRIMARY KEY,
      tipo_de_transacao VARCHAR,
      data DATE,
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

```bash
  POST   http://localhost:8081/clienteVO
  GET     http://localhost:8081/clienteVO
```