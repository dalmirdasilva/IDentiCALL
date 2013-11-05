package identicall;

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

    final private static char END_NUMBER_MARK = '\n';
    private DTMFSerial dtmfSerial;
    private PhoneNumberReadyListener phoneNumberReadyListener;
    private char[] incomingBuffer = new char[20];
    private int incomingBufferPoiter = 0;

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
                        if (input == END_NUMBER_MARK || incomingBufferPoiter >= 20) {
                            if (phoneNumberReadyListener != null) {
                                String phone = String.copyValueOf(incomingBuffer, 0, incomingBufferPoiter);
                                if (!phone.startsWith("OK")) {
                                    phoneNumberReadyListener.processPhoneNumber(phone);
                                }
                            }
                            incomingBufferPoiter = 0;
                        } else {
                            incomingBuffer[incomingBufferPoiter++] = (char) input;
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
