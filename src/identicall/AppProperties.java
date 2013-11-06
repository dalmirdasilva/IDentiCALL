package identicall;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {
    
    final private static String PROPERTIES_FILE = "src/config.properties";
    private static Properties properties;
    
    public static void setup() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(new File(PROPERTIES_FILE)));
    }

    public static Properties getProperties() throws IOException {
        if (properties == null) {
            setup();
        }
        return properties;
    }
}
