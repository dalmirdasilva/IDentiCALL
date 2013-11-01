
package dtmfserial;

import dao.CustomerDAO;
import dao.DAOFactory;
import dao.IncomingCallDAO;
import dao.hibernate.CustomerHibernateDAO;
import dao.hibernate.HibernateDAOFactory;
import dao.hibernate.IncomingCallHibernateDAO;
import entity.Customer;
import entity.IncomingCall;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.MainWindow;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

public class Identicall {
    private static SessionFactory sessionFactory;
    private static Session session;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

        try {
            AnnotationConfiguration c = new AnnotationConfiguration();
            c.addAnnotatedClass(IncomingCall.class);
            c.addAnnotatedClass(Customer.class);
            c.configure();
            sessionFactory = c.buildSessionFactory();
            session = sessionFactory.openSession();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        DAOFactory daoFactory = new HibernateDAOFactory();
        daoFactory.setSession(session);

        IncomingCallDAO incomingCallDAO = (IncomingCallDAO) daoFactory.getNewDAO(IncomingCallHibernateDAO.class);
        CustomerDAO customerDAO = (CustomerDAO) daoFactory.getNewDAO(CustomerHibernateDAO.class);
        
        IncomingCallNotifier incomingCallNotifier = new IncomingCallNotifier(incomingCallDAO, customerDAO);
        MainWindow window = new MainWindow();
        populateWithLastCalls(window, incomingCallDAO, customerDAO);
        window.setVisible(true);
    }

    private static void populateWithLastCalls(MainWindow window, IncomingCallDAO incomingCallDAO, CustomerDAO customerDAO) {
        Customer customer = customerDAO.findAll().get(0);
        window.setForm(customer);
    }
    
}
