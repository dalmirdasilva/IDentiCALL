package helper;

import identicall.PhoneLineWatcher;
import helper.AppProperties;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiagnosticRunner {
    
    final private static String LOCK_FILE_PATH_PREFIX = "/var/lock/LCK..";

    public static void runDiagnostic() throws IOException {
        Logger.getLogger(DiagnosticRunner.class.getName()).log(Level.INFO, "Running diagnostic.");
        removeLockFile();
    }

    private static void removeLockFile() throws IOException {
        Logger.getLogger(DiagnosticRunner.class.getName()).log(Level.INFO, "Trying to remove lock file.");
        String serialPortName = AppProperties.getUsedSerialPortName();
        File lock = new File(LOCK_FILE_PATH_PREFIX + serialPortName);
        if (lock.exists()) {
            lock.delete();
        }
    }
}
