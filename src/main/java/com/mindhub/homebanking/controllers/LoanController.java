package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanDto;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/loans")
    public List<LoanDto> getLoans(){
        return loanRepository.findAll().stream().map(LoanDto::new).collect(Collectors.toList());
    }
    @GetMapping("/loans/{id}")
    public LoanDto getLoanById(@PathVariable Long id){
        return new LoanDto(loanRepository.findById(id).orElse(null));
    }
}