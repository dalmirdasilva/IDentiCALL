package dao.hibernate;

import dao.CustomerDAO;
import entity.Customer;

public class CustomerHibernateDAO
        extends GenericHibernateDAO<Customer, String>
        implements CustomerDAO {
}