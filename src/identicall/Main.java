package identicall;

import audiorecord.VoiceRecorderFactory;
import helper.AppProperties;
import helper.Formater;
import dao.CityDAO;
import dao.CustomerDAO;
import dao.DAOFactory;
import dao.IncomingCallDAO;
import dao.hibernate.CityHibernateDAO;
import dao.hibernate.CustomerHibernateDAO;
import dao.hibernate.HibernateDAOFactory;
import dao.hibernate.IncomingCallHibernateDAO;
import helper.Normalizer;
import entity.City;
import entity.Customer;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import helper.PhoneNormilizer;
import helper.ReportGenerator;
import incomingcallnotification.IncomingCallDescriptor;
import incomingcallnotification.IncomingCallNotifier;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.MainWindow;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.Session;
import serialclient.SerialClientException;
import server.AppServer;
import view.Normalizing;

public class Main extends IncomingCallNotifier implements PhoneNumberReadyListener, CustomerSearcher {

    final private static String TIME_ZONE_ID = "Brazil/East";
    final private static String HIBERNATE_CFG_PROPERTY = "hibernatecfg";
    private SessionFactory sessionFactory;
    private Session session;
    private IncomingCallDAO incomingCallDAO;
    private CustomerDAO customerDAO;
    private CityDAO cityDAO;
    private MainWindow window;

    public static void main(String[] args) throws
            FileNotFoundException, IOException, NoSuchPortException, PortInUseException, 
            UnsupportedCommOperationException, TooManyListenersException, SerialClientException {
        Main mainApp = new Main();
        mainApp.addIncomingCallListener(new ReportGenerator());
        mainApp.addIncomingCallListener(AppServer.getInstance());
        mainApp.setup();
    }

    private void setup() throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException, IOException, SerialClientException {
        try {
            AnnotationConfiguration c = new AnnotationConfiguration();
            c.addAnnotatedClass(Customer.class);
            c.addAnnotatedClass(City.class);
            c.configure(AppProperties.getProperty(HIBERNATE_CFG_PROPERTY));
            sessionFactory = c.buildSessionFactory();
            session = sessionFactory.openSession();
        } catch (HibernateException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Failed to create sessionFactory object.", ex);
            throw new ExceptionInInitializerError(ex);
        }

        DAOFactory daoFactory = new HibernateDAOFactory();
        daoFactory.setSession(session);

        incomingCallDAO = (IncomingCallDAO) daoFactory.getNewDAO(IncomingCallHibernateDAO.class);
        customerDAO = (CustomerDAO) daoFactory.getNewDAO(CustomerHibernateDAO.class);
        cityDAO = (CityDAO) daoFactory.getNewDAO(CityHibernateDAO.class);

        try {
            final Normalizing normalizing = new Normalizing();
            normalizing.setVisible(true);
            Normalizer.normalize(customerDAO, cityDAO);
            new Thread() {
                
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    normalizing.setVisible(false);
                }
            }.start();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        final Main instance = this;
        PhoneLineWatcher incomingCallListener = new PhoneLineWatcher(instance);

        java.awt.EventQueue.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        window = new MainWindow(instance);
                        window.setVisible(true);
                    }
                });
    }

    @Override
    public void processPhoneNumber(String phone) {
        phone = PhoneNormilizer.normilize(phone);
        Map<String, String> properties = new HashMap<>();
        properties.put(Customer.CELL_PHONE_COLUMN, phone);
        properties.put(Customer.PRIMARY_BUSINESS_PHONE_COLUMN, phone);
        properties.put(Customer.RESIDENTIAL_PHONE_COLUMN, phone);
        List<Customer> customers = searchAndPopulateByProperties(properties, true);
        final IncomingCallDescriptor descriptor = new IncomingCallDescriptor(phone, customers);
        new Thread() {

            @Override
            public void run() {
                notifyIncomingCallListeners(descriptor);
            }
        }.start();
    }

    private List<Customer> searchAndPopulateByProperties(Map<String, String> properties, boolean fromLine) {
        List<Customer> customers = new ArrayList<>();
        if (window == null) {
            return customers;
        }
        try {
            customers = customerDAO.findByAttributes(properties);
        } catch (ObjectNotFoundException ex) {
            return customers;
        }
        String recentCallText = null;
        String phone = "";
        if (fromLine && properties.containsKey(Customer.CELL_PHONE_COLUMN)) {
            phone = properties.get(Customer.CELL_PHONE_COLUMN);
            recentCallText = getRecentCallText(phone);
        }
        int size = customers.size();
        Customer customer = size > 0 ? customers.get(0) : null;
        if (fromLine) {
            try {
                VoiceRecorderFactory.getInstance().startRecording(customer, phone);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        window.addRecentCaller(customer, recentCallText);
        return customers;
    }

    private String getRecentCallText(String phone) {
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone(TIME_ZONE_ID));
        return Formater.formatRecentCall(phone, calendar);
    }

    @Override
    public List<Customer> searchCustomer(Map<String, String> propertiesMap, boolean apeendRecentCall) {
        return searchAndPopulateByProperties(propertiesMap, apeendRecentCall);
    }
}
