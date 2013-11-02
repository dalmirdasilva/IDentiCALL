package identicall;

import dao.CustomerDAO;
import dao.DAOFactory;
import dao.IncomingCallDAO;
import dao.hibernate.CustomerHibernateDAO;
import dao.hibernate.HibernateDAOFactory;
import dao.hibernate.IncomingCallHibernateDAO;
import dtmfserial.DTMFSerial;
import entity.Customer;
import entity.IncomingCall;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TooManyListenersException;
import model.MainWindow;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

public class Identicall implements PhoneNumberReadyListener {

    private SessionFactory sessionFactory;
    private Session session;
    private IncomingCallDAO incomingCallDAO;
    private CustomerDAO customerDAO;

    public static void main(String[] args) throws
            FileNotFoundException, IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException {

        Identicall identicall = new Identicall();
        identicall.setUp();
    }

    private void setUp() throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException, IOException {
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

        incomingCallDAO = (IncomingCallDAO) daoFactory.getNewDAO(IncomingCallHibernateDAO.class);
        customerDAO = (CustomerDAO) daoFactory.getNewDAO(CustomerHibernateDAO.class);

        DTMFSerial dtmfSerial = new DTMFSerial();

        IncomingCallListener incomingCallNotifier = new IncomingCallListener(dtmfSerial, this);

        MainWindow window = new MainWindow();
        populateWithLastCalls(window, incomingCallDAO, customerDAO);

        window.setVisible(true);
    }

    private static void populateWithLastCalls(MainWindow window, IncomingCallDAO incomingCallDAO, CustomerDAO customerDAO) {
        Customer customer = customerDAO.findAll().get(0);
        window.setForm(customer);
    }

    @Override
    public void processNumber(int[] buffer) {
        System.out.println(buffer);
    }
}
