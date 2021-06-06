package com.edu.smartstudentcard.repository;

import com.edu.smartstudentcard.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

}
