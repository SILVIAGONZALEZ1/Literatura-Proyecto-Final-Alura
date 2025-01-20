package com.LiteraAlura.Literatura;

import com.LiteraAlura.Literatura.principal.PrincipalApp;
import com.LiteraAlura.Literatura.repository.AutorRepository;
import com.LiteraAlura.Literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PrincipalApp principal = new PrincipalApp(libroRepository, autorRepository);
		principal.ejecutar();
	}
}














