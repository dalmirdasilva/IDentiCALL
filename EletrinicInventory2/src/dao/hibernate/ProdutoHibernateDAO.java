package dao.hibernate;

import dao.ProdutoDAO;
import model.Produto;

public class ProdutoHibernateDAO
        extends GenericHibernateDAO<Produto, Long>
        implements ProdutoDAO {
}