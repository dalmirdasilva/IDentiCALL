package identicall;

import dao.CustomerDAO;
import dao.DAOFactory;
import dao.IncomingCallDAO;
import dao.hibernate.CustomerHibernateDAO;
import dao.hibernate.HibernateDAOFactory;
import dao.hibernate.IncomingCallHibernateDAO;
import entity.Customer;
import entity.IncomingCall;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MainWindow;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import serialclient.SerialClientException;

public class Main implements PhoneNumberReadyListener, CustomerSearcher {

    final private static String TIME_ZONE_ID = "Brazil/East";
    private SessionFactory sessionFactory;
    private Session session;
    private IncomingCallDAO incomingCallDAO;
    private CustomerDAO customerDAO;
    private MainWindow window;

    public static void main(String[] args) throws
            FileNotFoundException, IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException, SerialClientException {
        Main mainApp = new Main();
        mainApp.setup();
    }

    private void setup() throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException, IOException, SerialClientException {
        try {
            AnnotationConfiguration c = new AnnotationConfiguration();
            c.addAnnotatedClass(IncomingCall.class);
            c.addAnnotatedClass(Customer.class);
            c.configure("hibernate.mysql.cfg.xml");
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
    public void processPhoneNumber(String number) {
        Map<String, String> propertiesMap = new HashMap<>();
        propertiesMap.put(Customer.CELL_PHONE_COLUMN, number);
        propertiesMap.put(Customer.PRIMARY_BUSINESS_PHONE_COLUMN, number);
        propertiesMap.put(Customer.RESIDENTIAL_PHONE_COLUMN, number);
        searchAndPopulateByProperties(propertiesMap, true);
    }

    private int searchAndPopulateByProperties(Map<String, String> propertiesMap, boolean fromLine) {
        List<Customer> customers = customerDAO.findByAttributes(propertiesMap);
        String recentCallText = null;
        String phone = "";
        if (fromLine && propertiesMap.containsKey(Customer.CELL_PHONE_COLUMN)) {
            phone = propertiesMap.get(Customer.CELL_PHONE_COLUMN);
            recentCallText = getRecentCallText(phone);
        }
        int size = customers.size();
        Customer customer = size > 0 ? customers.get(0) : null;
        if (fromLine) {
            try {
                if (VoiceRecorder.isEnabled()) {
                    VoiceRecorder.startRecording(customer, phone);
                    window.startRecordTimer();
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        window.addRecentCaller(customer, recentCallText);
        return size;
    }

    private String getRecentCallText(String phone) {
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone(TIME_ZONE_ID));
        return Formater.formatRecentCall(phone, calendar);
    }

    @Override
    public int searchCustomer(Map<String, String> propertiesMap, boolean apeendRecentCall) {
        return searchAndPopulateByProperties(propertiesMap, apeendRecentCall);
    }
}
