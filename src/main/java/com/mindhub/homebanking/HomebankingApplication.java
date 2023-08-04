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

	//changes
	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository ){
		return (args) ->{
			Client client = new Client("Melba","Morel","Melba@mindhub.com");
			clientRepository.save(client);

			Account account1 = new Account("VIN001", LocalDate.now(),5000);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1),7500);
			accountRepository.save(account2);
			//client.addAccount(account1);
			//client.addAccount(account2);
			account1.setClient(client);
			account2.setClient(client);

			Client client2 = new Client("Leonel","Reindl","LeoReindl@gmail.com");
			clientRepository.save(client2);
			Account account3 = new Account("VIN003", LocalDate.now(),100000);
			accountRepository.save(account3);
			Account account4 = new Account("VIN004", LocalDate.now().plusDays(7),500000);
			accountRepository.save(account4);
			client2.addAccount(account3);
			client2.addAccount(account4);

		};
	}
}
