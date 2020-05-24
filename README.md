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

**1. Clonar o aplicativo**

```bash
https://github.com/Puglielli/contacorrente.git
```

**2. Instalar o Docker**

```bash
https://docs.docker.com/get-docker/
```

**3. Instalar e configurar o banco de dados *Cassandra***

  **3.1. Fazer o pull do repositorio da *Cassandra***

  `docker pull datastax/dse-server:5.1.18`

  **3.2. Executar o comando para para criar o container da *Cassandra***
  
  `docker run -e DS_LICENSE=accept --memory 4g --name cassandra -p 9042:9042 -d datastax/dse-server:5.1.18`

  **3.3. Executar o comando para fazer a copia do arquivo *cassandra.yaml* para dentro do container**
 
  `docker cp ./cassandra.yaml cassandra:/opt/dse/resources/cassandra/conf/`

  **3.4. Realizar stop e start do container *Cassandra***
  
  `docker stop cassandra`

  `docker start cassandra`

  **3.5. Executar o comando para configurar e criar as tabelas**
  
  `docker exec -it cassandra bash`

  **3.5.1. Executar o comando para logar na *Cassandra***
  
  `cqlsh -u cassandra -p cassandra`

  **3.5.2. Criar o Usuário**

  `CREATE ROLE root with SUPERUSER = true AND LOGIN = true and PASSWORD = 'root';`

  **3.5.3. Criar o Keyspace**

  `CREATE KEYSPACE itaudb WITH REPLICATION = {'class': 'SimpleStrategy','replication_factor' : 1};`

  `USE itaudb;`

  **3.5.1. Criar as tabelas**

  	```bash
      CREATE TABLE cliente (
      nome VARCHAR,
      cpf_cnpj VARCHAR PRIMARY KEY,
      tipo_de_cliente VARCHAR,
      endereco VARCHAR,
      profissao VARCHAR,
      razao_social VARCHAR,
      incr_estadual VARCHAR,
      num_conta VARCHAR
  );

    CREATE TABLE conta (
      num_conta VARCHAR PRIMARY KEY,
      agencia VARCHAR,
      dac INT,
      saldo DOUBLE
  );

    CREATE TABLE historico (
      cpf_cnpj VARCHAR PRIMARY KEY,
      tipo_de_transacao VARCHAR,
      data DATE,
      status INT
  );
```

**4. Instalar e configurar o *Kafka* e *Zookeeper***

  **4.1. Fazer o clone do repositorio que contem o *Kafka* e *Zookeeper***

  `git clone https://github.com/confluentinc/cp-docker-images`

  **4.2. Após clonado, navegue até a pasta cp-docker-images/examples/kafka-single-node e execute o comando:**
  
  `docker-compose up -d`

  **4.3. Execute o comando para listar os servições kafka e zookeeper**
  
  `docker-compose ps`

**4.4. Execute o comando para criar um topic no zookeeper**

  ```bash
  docker-compose exec kafka  \
  kafka-topics --create --topic foo --partitions 1 --replication-factor 1 --if-not-exists --zookeeper zookeeper:2181
  ```
**4.5. Execute o comando para validar se o Topic foi criado**

```bash
docker-compose exec kafka  \
    kafka-topics --describe --topic foo  --zookeeper zookeeper:2181
```

**5. importar o projeto no IDE**

Executar a class `ContacorrenteApplication`.

## Explore Rounting

```bash
  POST   http://localhost:8081/cliente
  GET     http://localhost:8081/cliente
```