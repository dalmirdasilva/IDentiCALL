package dao.hibernate;

import dao.VendaDAO;
import model.Venda;

public class VendaHibernateDAO
        extends GenericHibernateDAO<Venda, Long>
        implements VendaDAO {
}