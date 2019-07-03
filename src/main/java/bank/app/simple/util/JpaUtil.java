package bank.app.simple.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JpaUtil {
    private static volatile EntityManagerFactory entMngFactory;

    private static EntityManagerFactory getEntMngFactoryInstance(){
        if(entMngFactory == null){
            synchronized (EntityManagerFactory.class) {
                if (entMngFactory == null) {
                    entMngFactory = Persistence.createEntityManagerFactory("BankAppSimple");
                }
            }
        }
        return entMngFactory;
    }

    public static <T> Object getId(T entity){
        return entMngFactory.getPersistenceUnitUtil().getIdentifier(entity);
    }

    public static EntityManager createEntityManager() {
        return getEntMngFactoryInstance().createEntityManager();
    }

    public static void closeEntMngFactory() {
        getEntMngFactoryInstance().close();
    }

}
