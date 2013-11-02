package identicall;

import dao.CustomerDAO;
import dao.IncomingCallDAO;
import dtmfserial.DTMFSerial;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IncomingCallListener implements SerialPortEventListener {

    DTMFSerial dtmfSerial;
    PhoneNumberReadyListener phoneNumberReadyListener;
    
    int[] incomingBuffer = new int[20];
    int incomingBufferPoiter = 0;

    public IncomingCallListener(DTMFSerial dtmfSerial, PhoneNumberReadyListener phoneNumberReadyListener) throws
            NoSuchPortException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException, IOException {
        this.dtmfSerial = dtmfSerial;
        this.phoneNumberReadyListener = phoneNumberReadyListener;
        connectSerial();
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                int input = 0;
                try {
                    while ((input = dtmfSerial.getInputStream().read()) != -1) {
                        incomingBuffer[incomingBufferPoiter++] = input;
                        if (input == '\n' || incomingBufferPoiter >= 20) {
                            phoneNumberReadyListener.processNumber(incomingBuffer);
                            incomingBufferPoiter = 0;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(IncomingCallListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    private void connectSerial() throws
            NoSuchPortException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException, IOException {
        dtmfSerial.connect("/dev/ttyACM0");
        dtmfSerial.addSerialPortEventListener(this);

    }
}
