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

    // Consulta inteligente:
    // Se o parâmetro for NULL, ele ignora aquele filtro e traz tudo.
    // Isso permite filtrar só por data, só por bandeira, ou pelos dois.
    @Query("SELECT t FROM Transaction t WHERE " +
            "(:inicio IS NULL OR t.dataEvento >= :inicio) AND " +
            "(:fim IS NULL OR t.dataEvento <= :fim) AND " +
            "(:bandeira IS NULL OR :bandeira = '' OR UPPER(t.bandeira) = UPPER(:bandeira))")
    List<Transaction> findByFilters(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("bandeira") String bandeira);
}