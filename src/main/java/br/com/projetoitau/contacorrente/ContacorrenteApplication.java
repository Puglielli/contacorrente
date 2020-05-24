package br.com.projetoitau.contacorrente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories
public class ContacorrenteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContacorrenteApplication.class, args);
	}

}

