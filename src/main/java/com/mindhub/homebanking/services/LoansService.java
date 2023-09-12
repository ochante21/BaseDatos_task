package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoansDTO;

import java.util.List;

public interface LoansService {
    List<LoansDTO> getLoansDTO();
}
