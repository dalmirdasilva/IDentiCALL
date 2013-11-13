package identicall;

import helper.DiagnosticRunner;
import helper.AppProperties;
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
                    while ((input = serialClient.getInputStream().read()) != 1) {
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
                        "4791538642",
                        "4499770256",
                        "5199654276",
                        "5599791816",
                        "5399667486",
                        "5399720783",
                        "5596032434",
                        "4199620115",
                        "4791538642",
                        "1996394238",
                        "4691046321",
                        "1996394238",
                        "5399763183",
                        "5199642230",
                        "1996356004",
                        "1181031001",
                        "5199838478",
                        "5199687998",
                        "5391120364",
                        "5199545816",
                        "5181721011",
                        "4399940051",
                        "5384311056",
                        "4799792725",
                        "5199017256",
                        "5599474932",
                        "4999649313",
                        "5499823168",
                        "5381350116"
                    };
                    int i = 0;
                    while (true) {
                        String number = numbers[i++ % numbers.length];
                        System.out.println(number);
                        try {
                            Thread.sleep(5000);
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
