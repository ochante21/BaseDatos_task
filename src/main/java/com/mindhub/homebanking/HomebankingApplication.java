package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner init(ClientRepository ClientRepository, AccountRepository AccountRepository){
		return args ->{
			//crear al cliente
			Client cliente1= new Client("Melba","Morel","melba@mindhub.com");
			Client cliente2= new Client("bronco","broncudo","caballo.loco@bronco.com");

			//guerdar en la base de datos al cliente
			ClientRepository.save(cliente1);
			ClientRepository.save(cliente2);

			//crear una cuenta
			Account cuenta1 = new Account("VIN001", LocalDate.now(),5000.00);
			Account cuenta2 = new Account("VIN002", LocalDate.now().plusDays(1),7500.00);

			Account cuenta3= new Account("H2OlGA001",LocalDate.now().plusDays(3), 10000.00);

			//agregar la cuenta al cliente
			cliente1.addAccount(cuenta1);
			cliente1.addAccount(cuenta2);

			cliente2.addAccount(cuenta3);

			//guardar la cuenta
			AccountRepository.save(cuenta1);
			AccountRepository.save(cuenta2);
			AccountRepository.save(cuenta3);



		};

	}
}
