package vendaonline;

import dao.ClienteDAO;
import dao.ProdutoDAO;
import dao.DAOFactory;
import dao.VendaDAO;
import dao.hibernate.ClienteHibernateDAO;
import dao.hibernate.ProdutoHibernateDAO;
import dao.hibernate.HibernateDAOFactory;
import dao.hibernate.VendaHibernateDAO;
import java.util.Set;
import model.Cliente;
import model.Produto;
import model.Venda;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import view.JanelaPrincipal;

public class VendaOnline {

    private static SessionFactory sessionFactory;
    private static Session session;
    
    private static ProdutoDAO produtoDAO;
    private static ClienteDAO clienteDAO;
    private static VendaDAO vendaDAO;
    private static JanelaPrincipal janelaPrincipal;
    
    private static long currentUserId;
    
    public static void main(String[] args) {

        try {
            AnnotationConfiguration c = new AnnotationConfiguration();
            c.addAnnotatedClass(Produto.class);
            c.addAnnotatedClass(Cliente.class);
            c.addAnnotatedClass(Venda.class);
            c.configure();
            sessionFactory = c.buildSessionFactory();
            session = sessionFactory.openSession();
        } catch (Exception ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        DAOFactory daoFactory = new HibernateDAOFactory();
        daoFactory.setSession(session);

        produtoDAO = (ProdutoDAO) daoFactory.getNewDAO(ProdutoHibernateDAO.class);
        vendaDAO = (VendaDAO) daoFactory.getNewDAO(VendaHibernateDAO.class);
        clienteDAO = (ClienteDAO) daoFactory.getNewDAO(ClienteHibernateDAO.class);
        
        
        janelaPrincipal = new JanelaPrincipal();
        janelaPrincipal.setVisible(true);
        /*
        
        Cliente c = clienteDAO.findById((long)5, false);
        
        
        Venda v = new Venda(null, c, null);
       
        
        Set<Venda> vdas = c.getVendas();
        
        for (Venda v1 : vdas) {
            System.out.println(v1.getProduto().getId());
        }
        
        /*
        //Cliente c = new Cliente("paulo");
        //clienteDAO.saveEntity(c);
        List<Cliente> clienteList = clienteDAO.findByExample(new Cliente("paulo"), null);
        Cliente c = clienteList.get(0);
        /*
        Produto p = new Produto("abajur", "lindo abajur amarelo", (float) 9.99);
        Venda v = new Venda(new Date(), c, p);
        vendaDAO.saveEntity(v);
        
        Venda v = vendaDAO.findById((long)1, false);
        System.out.println(v.getProduto().getPreco());
        */
        
    }
    
    public static JanelaPrincipal getJanelaPrincipal() {
        return janelaPrincipal;
    }
    
    public static ProdutoDAO getProdutoDAO() {
        return produtoDAO;
    } 
    
    public static VendaDAO getVendaDAO() {
        return vendaDAO;
    } 
    
    public static ClienteDAO getClienteDAO() {
        return clienteDAO;
    } 
    
    public static void setCurrentUserId(long id) {
        currentUserId = id;
    }
    
    public static long getCurrentUserId() {
        return currentUserId;
    }
}
