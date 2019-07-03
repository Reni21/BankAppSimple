package bank.app.simple.service;

import bank.app.simple.entity.Transaction;
import bank.app.simple.entity.TransactionType;

import java.util.List;

public interface TransactionService {
    void addTransaction(Transaction transaction);

//    List<Transaction> findAllTransactions();
//
//    List<Transaction> findAllTransactionsByType(String personalNumber, TransactionType type);
//
//    List<Transaction> findAllUserTransactions(String personalNumber);
//
//    void deleteTransaction(Long id);
}
