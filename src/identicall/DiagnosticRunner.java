package identicall;

import java.io.File;

public class DiagnosticRunner {

    public static void runDiagnostic() {
        removeLockFile();
    }

    private static void removeLockFile() {
        String serialPortName = Main.getProperties().getProperty(PhoneLineWatcher.SERIAL_PORT_NAME_PROPERTY);
        File lock = new File("/var/lock/LCK.." + serialPortName);
        if (lock.exists()) {
            lock.delete();
        }
    }
}
