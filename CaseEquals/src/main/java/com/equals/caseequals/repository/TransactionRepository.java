package com.equals.caseequals.repository;

import com.equals.caseequals.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Método customizado para o relatório.
    // O Spring Data cria o SQL automaticamente baseado no nome do método.
    // "Find" (Buscar) "By" (Por) "DataEvento" (Campo) "Between" (Entre duas datas)
    List<Transaction> findByDataEventoBetween(LocalDate inicio, LocalDate fim);
}