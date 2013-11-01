package identicall;

import dao.CustomerDAO;
import dao.IncomingCallDAO;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class IncomingCallNotifier implements SerialPortEventListener {

    IncomingCallDAO incomingCallDAO;
    CustomerDAO customerDAO;

    public IncomingCallNotifier(IncomingCallDAO incomingCallDAO, CustomerDAO customerDAO) {
        this.incomingCallDAO = incomingCallDAO;
        this.customerDAO = customerDAO;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        System.out.println("incoming");
    }
}
