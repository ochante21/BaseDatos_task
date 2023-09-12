package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoansDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoansServiceImplement implements LoansService {
    @Autowired
    private LoanRepository loanRepository;
    @Override
    public List<LoansDTO> getLoansDTO() {
        List<Loan> loans = loanRepository.findAll();
        List<LoansDTO> loansDTO = loans.stream().map(loan -> new LoansDTO(loan)).collect(Collectors.toList());
        return loansDTO;
    }

}
