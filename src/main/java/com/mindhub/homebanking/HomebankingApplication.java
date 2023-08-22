package com.mindhub.homebanking;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {
	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

	}
	@Autowired
	PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,CardRepository cardRepository){
		return (args) ->{
			Client client = new Client("Melba","Morel","Melba@mindhub.com",passwordEncoder.encode("1"),RoleType.CLIENT);
			clientRepository.save(client);
			Account account1 = new Account("VIN001", LocalDate.now(),5000);
			client.addAccount(account1);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1),7500);
			client.addAccount(account2);
			accountRepository.save(account2);
			Client client2 = new Client("Leonel","Reindl","leo@gmail.com",passwordEncoder.encode("2"),RoleType.ADMIN);
			clientRepository.save(client2);
			Account account3 = new Account("VIN003", LocalDate.now(),100000);
			client2.addAccount(account3);
			accountRepository.save(account3);
			Account account4 = new Account("VIN004", LocalDate.now().plusDays(7),500000);
			client2.addAccount(account4);
			accountRepository.save(account4);
			Transaction transaction1 = new Transaction(TransactionType.DEBIT,-15000,"Description 1",LocalDate.now());
			transaction1.setAccount(account1);
			transactionRepository.save(transaction1);
			Transaction transaction2 = new Transaction(TransactionType.CREDIT,15000,"Description 2",LocalDate.now());
			transaction2.setAccount(account1);
			transactionRepository.save(transaction2);
			Transaction transaction3 = new Transaction(TransactionType.CREDIT,10000,"Description 3",LocalDate.now());
			transaction3.setAccount(account2);
			transactionRepository.save(transaction3);
			Transaction transaction4 = new Transaction(TransactionType.CREDIT,15000,"Description 4",LocalDate.now());
			transaction4.setAccount(account3);
			transactionRepository.save(transaction4);
			Transaction transaction5 = new Transaction(TransactionType.CREDIT,50000,"Description 5",LocalDate.now());
			account3.addTransaction(transaction5);
			transactionRepository.save(transaction5);

			//task4:Loans
			Loan loan1 = new Loan("Hipotecario",500000.0, Set.of(12,24,36,48,60));
			loanRepository.save(loan1);
			Loan loan2 = new Loan("Personal",100000.0, Set.of(6,12,24));
			loanRepository.save(loan2);
			Loan loan3 = new Loan("Automotriz",300000.0, Set.of(6,12,24,36));
			loanRepository.save(loan3);
			ClientLoan clientLoan1 = new ClientLoan(400000.0,60);//client,loan1);//con métodos
			client.addClientLoan(clientLoan1);//corrección Damian
			loan1.addClientLoan(clientLoan1);
			clientLoanRepository.save(clientLoan1);
			loanRepository.save(loan1);
			//--------------------------------
			ClientLoan clientLoan2 = new ClientLoan(50000.0,12);//client,loan2);hacer por método los campos que faltan
			client.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan2);
			clientLoanRepository.save(clientLoan2);
			//--------------------------------
			//cliente2
			ClientLoan clientLoan3 = new ClientLoan(100000.0,24);//,client2,loan2);agregar client2 y loan 2 por métodos
			client2.addClientLoan(clientLoan3);
			loan2.addClientLoan(clientLoan3);
			clientLoanRepository.save(clientLoan3);
			//--------------------------------
			ClientLoan clientLoan4 = new ClientLoan(200000.0,36);//,client2,loan3);agregar client2 y loan 2 por métodos
			client2.addClientLoan(clientLoan4);
			loan3.addClientLoan(clientLoan4);
			clientLoanRepository.save(clientLoan4);
			//--------------Task 5------------------
			Card card1 = new Card(client,CardType.DEBIT,CardColor.GOLD,"3325-6745-7876-4445",990,LocalDate.now(),LocalDate.now().plusYears(5));
			cardRepository.save(card1);
			Card card2 = new Card(client,CardType.CREDIT,CardColor.TITANIUM,"2234-6745-552-7888",750,LocalDate.now(),LocalDate.now().plusYears(5));
			cardRepository.save(card2);
			Card card3 = new Card(client2,CardType.CREDIT,CardColor.SILVER,"0705-9121-0769-0908",222,LocalDate.now(),LocalDate.now().plusYears(5));
			cardRepository.save(card3);
			//Task 6
			/*--------------------------------------------------------------------------------------*/

		};
	}
}