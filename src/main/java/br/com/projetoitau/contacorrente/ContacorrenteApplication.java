package br.com.projetoitau.contacorrente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableCassandraRepositories
@EnableKafka
public class ContacorrenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContacorrenteApplication.class, args);
	}

}

