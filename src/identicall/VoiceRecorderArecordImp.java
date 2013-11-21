package identicall;

import helper.AppProperties;
import entity.Customer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoiceRecorderArecordImp extends VoiceRecorder {

    final private static String RECORDER_COMMAND_PROPERTY = "recordercommand";
    final private static String RECORDER_PROPERTY = "recorder";
    private static Process recorderProcess;
    private static int lastRecordPIDNumber = 0;

    VoiceRecorderArecordImp() {
        super();
    }

    private static void lauchRecordProcess(File outputFile) throws IOException {
        stopRecordProcess();
        String command = AppProperties.getProperty(RECORDER_COMMAND_PROPERTY);
        command = String.format(command, outputFile.getAbsolutePath());
        Runtime runtime = Runtime.getRuntime();
        recorderProcess = runtime.exec(command);
        try {
            Field pidField = recorderProcess.getClass().getDeclaredField("pid");
            pidField.setAccessible(true);
            lastRecordPIDNumber = pidField.getInt(recorderProcess);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(VoiceRecorderArecordImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void stopRecordProcess() throws IOException {
        if (recorderProcess != null) {
            recorderProcess.destroy();
            if (lastRecordPIDNumber > 0) {
                Runtime runtime = Runtime.getRuntime();
                Object recorder = AppProperties.getProperty(RECORDER_PROPERTY);
                Process recorders = runtime.exec("killall " + recorder);
                try {
                    recorders.waitFor();
                } catch (InterruptedException ex) {
                    Logger.getLogger(VoiceRecorderArecordImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            recorderProcess = null;
        }
    }

    @Override
    protected void startRecordingImp(File outputFile) throws IOException {
        lauchRecordProcess(outputFile);
    }

    @Override
    protected void stopRecordingImp() throws IOException {
        stopRecordProcess();
    }
}
