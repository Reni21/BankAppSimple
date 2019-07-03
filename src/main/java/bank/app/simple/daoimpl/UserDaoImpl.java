package bank.app.simple.daoimpl;

import bank.app.simple.dao.UserDao;
import bank.app.simple.entity.User;
import bank.app.simple.util.JpaUtil;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findUserByPersonalNumber(String personalNumber) {
        entM = JpaUtil.createEntityManager();
        Query query = entM
                .createQuery("SELECT u FROM User u WHERE u.personalNumber = :personalNumber");
        query.setParameter("personalNumber", personalNumber);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            entM.close();
        }
    }
}
