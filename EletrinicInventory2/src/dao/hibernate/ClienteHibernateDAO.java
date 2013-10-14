package dao.hibernate;

import dao.ClienteDAO;
import model.Cliente;

public class ClienteHibernateDAO
        extends GenericHibernateDAO<Cliente, Long>
        implements ClienteDAO {
}