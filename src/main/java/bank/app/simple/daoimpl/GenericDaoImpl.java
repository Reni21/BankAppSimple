package bank.app.simple.daoimpl;

import bank.app.simple.dao.GenericDao;
import bank.app.simple.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public class GenericDaoImpl <T, PK extends Serializable> implements GenericDao <T, PK> {
    protected static EntityManager entM = null;
    private Class<T> clazz;

    public GenericDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public PK create(T newInstance) {
        entM = JpaUtil.createEntityManager();
        entM.getTransaction().begin();
        try {
            entM.persist(newInstance);
            entM.getTransaction().commit();
            Object id = JpaUtil.getId(newInstance);
            return (PK)id;
        } catch (Exception ex) { //  Error: 1062
            entM.getTransaction().rollback();
            return null;
        } finally {
            entM.close();
        }
    }

    @Override
    public T find(PK id) {
        entM = JpaUtil.createEntityManager();
        try {
            return entM.find(clazz, id);
        } catch (EntityNotFoundException ex) {
            return null;
        } finally {
            entM.close();
        }
    }

    @Override
    public void update(T transientObject) {
        entM = JpaUtil.createEntityManager();
        entM.getTransaction().begin();
        try {
            entM.merge(transientObject); // throw IllegalArgumentException
            entM.getTransaction().commit();
        } catch (Exception ex) {
            entM.getTransaction().rollback();
        } finally {
            entM.close();
        }
    }

    @Override
    public void delete(T persistentObject, PK objId) {
        entM = JpaUtil.createEntityManager();
        entM.getTransaction().begin();
        try {
            if (entM.contains(persistentObject)) {
                entM.remove(persistentObject);
            } else {
                T object = entM.getReference(clazz, objId);
                entM.remove(object);
            }
            entM.getTransaction().commit();
        } catch (Exception ex) {
            entM.getTransaction().rollback();
        } finally {
            entM.close();
        }
    }

    @Override
    public List<T> findAll() {
        entM = JpaUtil.createEntityManager();
        try {
            Query query = entM.createQuery(
                    "SELECT o FROM " + clazz.getSimpleName() + " o", clazz);
            return (List<T>) query.getResultList();
        } finally {
            entM.close();
        }
    }

}
