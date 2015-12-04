package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {

    final private static String PROPERTIES_FILE = "/work/opensource/personal/IDentiCALL/src/config.properties";
    private static File file;
    private static Properties properties;
    private static String usedSerialPortName;

    public static String getUsedSerialPortName() {
        return usedSerialPortName;
    }

    public static void setUsedSerialPortName(String usedSerialPortName) {
        AppProperties.usedSerialPortName = usedSerialPortName;
    }

    public static void setup() throws IOException {
        properties = new Properties();
        file = new File(PROPERTIES_FILE);
        properties.load(new FileInputStream(file));
    }

    private static void checkProperties() throws IOException {
        if (properties == null) {
            setup();
        }
    }

    public static Properties getProperties() throws IOException {
        checkProperties();
        return properties;
    }

    public static String getProperty(String property) throws IOException {
        checkProperties();
        return properties.getProperty(property);
    }

    public static void setProperty(String property, String value) throws IOException {
        checkProperties();
        properties.setProperty(property, value);
    }

    public static void storeProperties() throws FileNotFoundException, IOException {
        checkProperties();
        if (properties != null && file != null) {
            FileOutputStream fos = new FileOutputStream(file);
            properties.store(fos, "");
        }
    }
}
