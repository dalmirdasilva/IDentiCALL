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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.TooManyListenersException;
import model.MainWindow;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

public class Identicall implements PhoneNumberReadyListener, CustomerSearchListener {

    private SessionFactory sessionFactory;
    private Session session;
    private IncomingCallDAO incomingCallDAO;
    private CustomerDAO customerDAO;
    private MainWindow window;

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

        final Identicall instance = this;

        DTMFSerial dtmfSerial = new DTMFSerial();
        IncomingCallListener incomingCallListener = new IncomingCallListener(dtmfSerial, instance);

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
        List<Customer> examples = new ArrayList<>();
        Customer cellPhoneExample = new Customer();
        cellPhoneExample.setCellPhone(number);
        Customer businessPhoneExample = new Customer();
        businessPhoneExample.setBusinessPhone(number);
        Customer residentialBusinessPhone = new Customer();
        residentialBusinessPhone.setResidentialPhone(number);
        examples.add(cellPhoneExample);
        examples.add(businessPhoneExample);
        examples.add(residentialBusinessPhone);
        searchAndPopulateByExamples(examples, number, true);
    }

    private int searchAndPopulateByExamples(List<Customer> examples, String phone, boolean appendIncomingCall) {
        List<Customer> customers = customerDAO.findByExample(examples, null);
        if (customers != null && !customers.isEmpty()) {
            Customer customer = customers.get(0);
            window.populateCustomer(customer);
        } else {
            window.emptyCustomer();
        }
        if (appendIncomingCall) {
            window.appendRecentCall(buildRecentCallString(phone));
        }
        return (customers != null) ? customers.size() : (int) 0;
    }

    @Override
    public int onCustomerSearch(List<Customer> examples, String search, boolean appendIncomingCall) {
        return searchAndPopulateByExamples(examples, search, appendIncomingCall);
    }

    private String buildRecentCallString(String phone) {
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Brazil/East"));
        return formatPhoneToDisplay(phone) + " @ " + formatDateTimeToDisplay(calendar);
    }

    private String formatPhoneToDisplay(String phone) {
        if (phone.length() < 10) {
            return phone;
        }
        StringBuilder toDisplay = new StringBuilder();
        toDisplay.append("(");
        toDisplay.append(phone.substring(0, 2));
        toDisplay.append(") ");
        toDisplay.append(phone.substring(2, 10));
        return toDisplay.toString();
    }

    private String formatDateTimeToDisplay(Calendar calendar) {
        StringBuilder toDisplay = new StringBuilder();
        toDisplay.append(String.format("%02d", calendar.get(Calendar.HOUR)));
        toDisplay.append(":");
        toDisplay.append(String.format("%02d", calendar.get(Calendar.MINUTE)));
        toDisplay.append(" ");
        toDisplay.append(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
        toDisplay.append("/");
        toDisplay.append(String.format("%02d", calendar.get(Calendar.MONTH)));
        toDisplay.append("/");
        toDisplay.append(String.format("%04d", calendar.get(Calendar.YEAR)));
        return toDisplay.toString();
    }
}
