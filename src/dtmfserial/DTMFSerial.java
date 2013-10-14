package dtmfserial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

public class DTMFSerial {

    private SerialPort serialPort;
    private SerialPortEventListener serialPortEventListener;

    public DTMFSerial(SerialPortEventListener serialPortEventListener) {
        this.serialPortEventListener = serialPortEventListener;
    }

    void connect(String portName) throws
            NoSuchPortException,
            PortInUseException,
            UnsupportedCommOperationException,
            TooManyListenersException,
            IOException {

        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {

            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {

                serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                serialPort.notifyOnDataAvailable(true);
                serialPort.addEventListener(serialPortEventListener);
            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    public InputStream getInputStream() throws IOException {
        return serialPort.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return serialPort.getOutputStream();
    }
}