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

public class PhoneLineWatcher implements SerialPortEventListener {

    final private static String READY_INITIAL_MARK = "OK";
    final private static char END_NUMBER_MARK = '\n';
    final private static int PHONE_NUMBER_BUFFER_SIZE = 20;
    final public static String SERIAL_PORT_PATH_PROPERTY = "serialportpath";
    final public static String SERIAL_PORT_NAME_PROPERTY = "serialportname";
    final public static String FAKE_SERIAL_PROPERTY = "fakeserail";

    private final SerialClient serialClient;
    private final PhoneNumberReadyListener phoneNumberReadyListener;
    private final char[] incomingBuffer = new char[PHONE_NUMBER_BUFFER_SIZE];
    private int incomingBufferPoiter = 0;

    public PhoneLineWatcher(PhoneNumberReadyListener phoneNumberReadyListener) throws
            NoSuchPortException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException, IOException, SerialClientException {
        this.serialClient = new SerialClient();
        this.phoneNumberReadyListener = phoneNumberReadyListener;
        try {
            connectSerialClient();
        } catch (SerialClientException | NoSuchPortException ex) {
            DiagnosticRunner.runDiagnostic();
            connectSerialClient();
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
                try {
                    int input = 0;
                    while ((input = serialClient.getInputStream().read()) != -1) {
                        if (input == END_NUMBER_MARK || incomingBufferPoiter >= PHONE_NUMBER_BUFFER_SIZE) {
                            if (phoneNumberReadyListener != null) {
                                String phone = String.copyValueOf(incomingBuffer, 0, incomingBufferPoiter);
                                if (!phone.startsWith(READY_INITIAL_MARK)) {
                                    phoneNumberReadyListener.processPhoneNumber(phone);
                                }
                            }
                            incomingBufferPoiter = 0;
                        } else {
                            incomingBuffer[incomingBufferPoiter++] = (char) input;
                        }
                    }
                } catch (IOException | SerialClientException ex) {
                    Logger.getLogger(PhoneLineWatcher.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    private void connectSerialClient() throws
            NoSuchPortException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException, IOException, SerialClientException {

        if (AppProperties.getProperties().getProperty(FAKE_SERIAL_PROPERTY).equals("false")) {

            serialClient.connect(AppProperties.getProperties().getProperty(SERIAL_PORT_PATH_PROPERTY));
            serialClient.addSerialPortEventListener(this);
        } else {

            new Thread() {

                @Override
                public void run() {

                    String[] numbers = new String[]{
                        "(47)9153-8642",
                        "(44)9977-0256",
                        "(51)9965-4276",
                        "(55)9979-1816",
                        "(53)9966-7486",
                        "(53)9972-0783",
                        "(55)9603-2434",
                        "(41)9962-0115",
                        "(47)9153-8642",
                        "(19)9639-4238",
                        "(46)9104-6321",
                        "(19)9639-4238",
                        "(53)9976-3183",
                        "(51)9964-2230",
                        "(19)9635-6004",
                        "(11)8103-1001",
                        "(51)9983-8478",
                        "(51)9968-7998",
                        "(53)9112-0364",
                        "(51)9954-5816",
                        "(51)8172-1011",
                        "(43)9994-0051",
                        "(53)8431-1056",
                        "(47)9979-2725",
                        "(51)9901-7256",
                        "(55)9947-4932",
                        "(49)9964-9313",
                        "(54)9982-3168",
                        "(53)8135-0116"
                    };
                    int i = 0;
                    while (true) {
                        String number = numbers[i++ % numbers.length];
                        System.out.println(number);
                        try {
                            Thread.sleep(10000);
                            phoneNumberReadyListener.processPhoneNumber(number);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PhoneLineWatcher.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }.start();
        }
    }
}
