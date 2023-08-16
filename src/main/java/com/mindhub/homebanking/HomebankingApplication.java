package com.mindhub.homebanking;

import com.mindhub.homebanking.Enums.CardColor;
import com.mindhub.homebanking.Enums.CardType;
import com.mindhub.homebanking.Enums.TransactionType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner init(ClientRepository ClientRepository,
								  AccountRepository AccountRepository,
								  TransactionRepository TransactionRepository,
								  LoanRepository LoanRepository,
								  ClientLoanRepository ClientLoanRepository,
								  CardRepository CardRepository){
		return args ->{
			//crear al cliente
			Client cliente1= new Client("Melba","Morel","melba@mindhub.com");
			Client cliente2= new Client("Bronco","Broncudo","caballo.loco@bronco.com");

			//guardar en la base de datos al cliente
			ClientRepository.save(cliente1);
			ClientRepository.save(cliente2);

			//crear una cuenta
			Account cuenta1 = new Account("VIN001", LocalDate.now().minusDays(1),5000.00);
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

			//crear transacciones
			Transaction transaction1= new Transaction(TransactionType.CREDIT,30000.00,"transferencia familiar", LocalDateTime.now());
			Transaction transaction2= new Transaction(TransactionType.DEBIT,23000.00,"cena familiar", LocalDateTime.now());
			Transaction transaction3= new Transaction(TransactionType.DEBIT,1300.00,"propina", LocalDateTime.now());
			Transaction transaction4= new Transaction(TransactionType.CREDIT,1000.00,"transferecia", LocalDateTime.now());
			Transaction transaction5= new Transaction(TransactionType.DEBIT,9000.00,"playera", LocalDateTime.now());
			Transaction transaction6= new Transaction(TransactionType.CREDIT,10000.00,"sueldo", LocalDateTime.now());
			Transaction transaction7= new Transaction(TransactionType.DEBIT,1000.00,"sube", LocalDateTime.now());
			Transaction transaction8= new Transaction(TransactionType.DEBIT,15000.00,"comida", LocalDateTime.now());

			Transaction transaction9= new Transaction(TransactionType.CREDIT,10000.00,"sueldo", LocalDateTime.now());
			Transaction transaction10= new Transaction(TransactionType.DEBIT,1000.00,"sube", LocalDateTime.now());
			Transaction transaction11= new Transaction(TransactionType.DEBIT,9000.00,"playera", LocalDateTime.now());
			Transaction transaction12= new Transaction(TransactionType.DEBIT,23000.00,"cena familiar", LocalDateTime.now());
			Transaction transaction13= new Transaction(TransactionType.DEBIT,15000.00,"comida", LocalDateTime.now());


			//agregar transacciones a la cuenta
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

			//crear tipos de prestamos

			Loan loan1=new Loan("Hipotecario",500000.00, List.of(12,24,36,48,60));
			Loan loan2=new Loan("Personal",100000.00, List.of(6,12,24));
			Loan loan3=new Loan("Automotriz",300000.00, List.of(6,12,24,36));

			//guardar en la abse de datos
			LoanRepository.save(loan1);
			LoanRepository.save(loan2);
			LoanRepository.save(loan3);

			//crear prestamos de clientes
			ClientLoan clientLoan1=new ClientLoan(400000.00,60,loan1.getName());
			ClientLoan clientLoan2=new ClientLoan(50000.00,12,loan2.getName());
			ClientLoan clientLoan3=new ClientLoan(100000.00,24,loan2.getName());
			ClientLoan clientLoan4=new ClientLoan(200000.00,36,loan3.getName());

			//agregar prestamos a clientes
			//agregar al cliente1 la cantidad y el tipo de prestamos
			cliente1.addClientLoan(clientLoan1);
			loan1.addClientLoan(clientLoan1);

			cliente1.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan2);

			//agregar al cliente la cantidad y el tipo de prestamos
			cliente2.addClientLoan(clientLoan3);
			loan2.addClientLoan(clientLoan3);

			cliente2.addClientLoan(clientLoan4);
			loan3.addClientLoan(clientLoan4);

			//guardar los prestamos
			ClientLoanRepository.save(clientLoan1);
			ClientLoanRepository.save(clientLoan2);
			ClientLoanRepository.save(clientLoan3);
			ClientLoanRepository.save(clientLoan4);

			//crear tarjeta para clientes
			Card card1= new Card(cliente1.toString(), CardType.DEBIT, CardColor.GOLD,1234567890L,322,LocalDate.now(),LocalDate.now().plusYears(5));
			Card card2= new Card(cliente1.toString(), CardType.CREDIT, CardColor.TITANIUM,9876543210L,101,LocalDate.now(),LocalDate.now().plusYears(5));
			Card card3= new Card(cliente2.toString(), CardType.CREDIT, CardColor.SILVER,4321876509L,666,LocalDate.now(),LocalDate.now().plusYears(2));

			//asignar las cartas a clientes
			cliente1.addCard(card1);
			cliente1.addCard(card2);
			cliente2.addCard(card3);

			//guardar en la base
			CardRepository.save(card1);
			CardRepository.save(card2);
			CardRepository.save(card3);

		};

	}
}
