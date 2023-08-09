package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner init(ClientRepository ClientRepository, AccountRepository AccountRepository, TransactionRepository TransactionRepository){
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

			//agregar trasferencias
			Transaction transaction1= new Transaction(TransactionType.credit,30000.00,"transferencia familiar", LocalDateTime.now());
			Transaction transaction2= new Transaction(TransactionType.debit,23000.00,"cena familiar", LocalDateTime.now());
			Transaction transaction3= new Transaction(TransactionType.debit,1300.00,"propina", LocalDateTime.now());
			Transaction transaction4= new Transaction(TransactionType.credit,1000.00,"transferecia", LocalDateTime.now());
			Transaction transaction5= new Transaction(TransactionType.debit,9000.00,"playera", LocalDateTime.now());
			Transaction transaction6= new Transaction(TransactionType.credit,10000.00,"sueldo", LocalDateTime.now());
			Transaction transaction7= new Transaction(TransactionType.debit,1000.00,"sube", LocalDateTime.now());
			Transaction transaction8= new Transaction(TransactionType.debit,15000.00,"comida", LocalDateTime.now());

			Transaction transaction9= new Transaction(TransactionType.credit,10000.00,"sueldo", LocalDateTime.now());
			Transaction transaction10= new Transaction(TransactionType.debit,1000.00,"sube", LocalDateTime.now());
			Transaction transaction11= new Transaction(TransactionType.debit,9000.00,"playera", LocalDateTime.now());
			Transaction transaction12= new Transaction(TransactionType.debit,23000.00,"cena familiar", LocalDateTime.now());
			Transaction transaction13= new Transaction(TransactionType.debit,15000.00,"comida", LocalDateTime.now());


			//agregar transacciones ala cuenta
			cuenta1.addTransaction(transaction1);
			cuenta1.addTransaction(transaction2);
			cuenta1.addTransaction(transaction3);
			cuenta1.addTransaction(transaction4);

			cuenta2.addTransaction(transaction5);
			cuenta2.addTransaction(transaction6);
			cuenta2.addTransaction(transaction7);
			cuenta2.addTransaction(transaction8);

			cuenta3.addTransaction(transaction9);
			cuenta3.addTransaction(transaction10);
			cuenta3.addTransaction(transaction11);
			cuenta3.addTransaction(transaction12);
			cuenta3.addTransaction(transaction13);

			//guardar la transaccion
			TransactionRepository.save(transaction1);
			TransactionRepository.save(transaction2);
			TransactionRepository.save(transaction3);
			TransactionRepository.save(transaction4);

			TransactionRepository.save(transaction5);
			TransactionRepository.save(transaction6);
			TransactionRepository.save(transaction7);
			TransactionRepository.save(transaction8);

			TransactionRepository.save(transaction10);
			TransactionRepository.save(transaction11);
			TransactionRepository.save(transaction12);
			TransactionRepository.save(transaction13);
			TransactionRepository.save(transaction9);




		};

	}
}
