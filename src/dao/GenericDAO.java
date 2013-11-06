
package dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Criterion;

public interface GenericDAO<T, K extends Serializable> {
 
    T findById(K id, boolean lock);
 
    List<T> findAll();
 
    List<T> findByExample(List<T> exampleInstanceList, String[] excludeProperty);
 
    List<T> findByAttributes(Map<String, String> attributeMap);
    
    T saveEntity(T entity);
 
    void deleteEntity(T entity);
    
    List<T> findByCriteria(Criterion... criterion);
}