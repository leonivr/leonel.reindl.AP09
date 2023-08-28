package com.mindhub.homebanking.repositories;
import com.mindhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account,Long > {

    String findByNumber(String number);//1° forma, Long number

    boolean  existsByNumber(String number);//2° forma
}
