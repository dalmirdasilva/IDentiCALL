package dao.hibernate;

import dao.GenericDAO;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

public abstract class GenericHibernateDAO<T, K extends Serializable>
        implements GenericDAO<T, K> {

    private Class<T> persistentClass;
    private Session session = null;

    public GenericHibernateDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    public void setSession(Session session) {
        this.session = session;
    }

    protected Session getSession() {
        if (session == null) {
            throw new IllegalStateException("Session has not been set on DAO before usage");
        }
        return session;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(K id, boolean lock) {
        session.getTransaction().begin();
        T entity;
        if (lock) {
            entity = (T) session.load(getPersistentClass(), id, LockMode.UPGRADE);
        } else {
            entity = (T) session.load(getPersistentClass(), id);
        }
        session.getTransaction().commit();
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return findByCriteria();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
        session.getTransaction().begin();
        Criteria criteriacrit = session.createCriteria(getPersistentClass());
        Example example = Example.create(exampleInstance);
        if (excludeProperty != null) {
            for (String exclude : excludeProperty) {
                example.excludeProperty(exclude);
            }
        }
        criteriacrit.add(example);
        List<T> list = criteriacrit.list();
        session.getTransaction().commit();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T saveEntity(T entity) {
        session.getTransaction().begin();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
        return entity;
    }

    @Override
    public void deleteEntity(T entity) {
        session.getTransaction().begin();
        session.delete(entity);
        session.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByCriteria(Criterion... criterion) {
        session.getTransaction().begin();
        Criteria criteria = session.createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            criteria.add(c);
        }
        List<T> list = criteria.list();
        session.getTransaction().commit();
        return list;
    }
}
