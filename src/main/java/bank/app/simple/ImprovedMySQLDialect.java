package bank.app.simple;

import org.hibernate.dialect.MySQL8Dialect;

public class ImprovedMySQLDialect extends MySQL8Dialect {

//    @Override
//    public boolean dropConstraints() {
//        // We don't need to drop constraints before dropping tables, that just leads to error
//        // messages about missing tables when we don't have a schema in the database
//        return false;
//    }
}
