package identicall;

import serialclient.SerialClient;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import serialclient.SerialClientException;

public class IncomingCallReader implements SerialPortEventListener {

    final private static char END_NUMBER_MARK = '\n';
    private SerialClient serialClient;
    private PhoneNumberReadyListener phoneNumberReadyListener;
    private char[] incomingBuffer = new char[20];
    private int incomingBufferPoiter = 0;

    public IncomingCallReader(PhoneNumberReadyListener phoneNumberReadyListener) throws
            NoSuchPortException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException, IOException {
        this.serialClient = new SerialClient();
        this.phoneNumberReadyListener = phoneNumberReadyListener;
        try {
            connectSerialClient();
        } catch (SerialClientException ex) {
            Logger.getLogger(IncomingCallReader.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    while ((input = serialClient.getInputStream().read()) != -1) {
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
                } catch (IOException | SerialClientException ex) {
                    Logger.getLogger(IncomingCallReader.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    private void connectSerialClient() throws
            NoSuchPortException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException, IOException, SerialClientException {
        serialClient.connect("/dev/ttyACM0");
        serialClient.addSerialPortEventListener(this);
    }
}
