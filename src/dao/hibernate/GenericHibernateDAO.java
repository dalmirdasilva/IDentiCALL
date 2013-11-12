package dao.hibernate;

import dao.GenericDAO;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

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
    public List<T> findByExample(List<T> exampleInstanceList, String[] excludeProperty) {
        Criteria criteria = session.createCriteria(getPersistentClass());
        if (exampleInstanceList != null) {
            Disjunction or = Restrictions.disjunction();
            for (T exampleInstance : exampleInstanceList) {
                Example example = Example.create(exampleInstance);
                example.enableLike();
                if (excludeProperty != null) {
                    for (String exclude : excludeProperty) {
                        example.excludeProperty(exclude);
                    }
                }
                or.add(example);
            }
            criteria.add(or);
        }
        List<T> list = criteria.list();
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByAttributes(Map<String, String> attributeMap) {
        Criteria criteria = session.createCriteria(getPersistentClass());
        if (attributeMap != null) {
            Disjunction disjunction = Restrictions.disjunction();
            for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
                disjunction.add(Restrictions.ilike(entry.getKey(), "%" + entry.getValue() + "%"));
            }
            criteria.add(disjunction);
        }
        List<T> list = criteria.list();
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
