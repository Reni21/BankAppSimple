package bank.app.simple.serviceimpl;

import bank.app.simple.dao.TransactionDao;
import bank.app.simple.entity.Transaction;
import bank.app.simple.service.TransactionService;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionDao transDao;

    public TransactionServiceImpl(TransactionDao transDao) {
        this.transDao = transDao;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        Long id = transDao.create(transaction);
        transaction.setId(id);
    }
}
