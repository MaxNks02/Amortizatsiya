package com.example.amortizatsiya.repository;

import com.example.amortizatsiya.model.lov.HistoryTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryTransactionsRepo extends JpaRepository<HistoryTransactions, Long> {
}
