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

@SpringBootApplication
public class HomebankingApplication {

	//changes
	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args) ->{
			Client client = new Client("Melba","Morel","Melba@mindhub.com");
			clientRepository.save(client);
			Account account1 = new Account("VIN001", LocalDate.now(),5000);
			client.addAccount(account1);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1),7500);
			client.addAccount(account2);
			accountRepository.save(account2);
			Client client2 = new Client("Leonel","Reindl","LeoReindl@gmail.com");
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
		};
	}
}
