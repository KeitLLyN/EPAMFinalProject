package utils.dao.interfaces;

import java.util.List;

public interface GenericDao<T extends Identified> {
    T getByKey(String id,int key);
    void update(T object);
    void delete(T object);
    void insert(T object);
    List<T> findBy(String ...strings);
    List<T> getAll();
}
