
package dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;

public interface GenericDAO<T, K extends Serializable> {
 
    T findById(K id, boolean lock);
 
    List<T> findAll();
 
    List<T> findByExample(T exampleInstance, String[] excludeProperty);
 
    T saveEntity(T entity);
 
    void deleteEntity(T entity);
    
    List<T> findByCriteria(Criterion... criterion);
}