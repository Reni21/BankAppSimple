package bank.app.simple.daoimpl;

import bank.app.simple.dao.TransactionDao;
import bank.app.simple.entity.Transaction;

public class TransactionDaoImpl extends GenericDaoImpl<Transaction, Long> implements TransactionDao {

    public TransactionDaoImpl() {
        super(Transaction.class);
    }
}
