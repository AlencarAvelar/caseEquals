package com.equals.caseequals.repository;

import com.equals.caseequals.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Adicionei o CAST para o PostgreSQL entender os tipos mesmo quando forem nulos
    @Query("SELECT t FROM Transaction t WHERE " +
            "(CAST(:inicio AS date) IS NULL OR t.dataPrevistaPagamento >= :inicio) AND " +
            "(CAST(:fim AS date) IS NULL OR t.dataPrevistaPagamento <= :fim) AND " +
            "(CAST(:bandeira AS text) IS NULL OR :bandeira = '' OR UPPER(t.bandeira) = UPPER(:bandeira))")
    List<Transaction> findByFilters(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("bandeira") String bandeira);
}