package bank.app.simple.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {
    PK create(T newInstance); // primary key
    T find(PK id);
    void update(T transientObject);
    void delete(T persistentObject, PK id);
    List<T> findAll();
}