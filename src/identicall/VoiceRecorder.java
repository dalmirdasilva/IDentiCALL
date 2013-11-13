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

public class VoiceRecorder {

    final private static String RECORDER_COMMAND_PROPERTY = "recordercommand";
    final private static String RECORDER_PROPERTY = "recorder";
    final private static String CONVERT_COMMAND_PROPERTY = "convertcommand";
    final private static String OUTPUT_PATH_PROPERTY = "voicedir";
    final private static String AUTO_RECORD_PROPERTY = "autorecord";
    private static boolean enabled;
    private static boolean recording = false;
    private static Process recorderProcess;
    private static int lastRecordPIDNumber = 0;

    static {
        try {
            enabled = AppProperties.getProperties().getProperty(AUTO_RECORD_PROPERTY).endsWith("true");
        } catch (IOException ex) {
            enabled = false;
            Logger.getLogger(VoiceRecorder.class.getName()).log(Level.SEVERE, "Cannot determine auto record property.", ex);
        }
    }

    private static void lauchRecordProcess(File outputFile) throws IOException {
        stopRecordProcess();
        String command = AppProperties.getProperties().getProperty(RECORDER_COMMAND_PROPERTY);
        command = String.format(command, outputFile.getAbsolutePath());
        Runtime runtime = Runtime.getRuntime();
        recorderProcess = runtime.exec(command);
        try {
            Field pidField = recorderProcess.getClass().getDeclaredField("pid");
            pidField.setAccessible(true);
            lastRecordPIDNumber = pidField.getInt(recorderProcess);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(VoiceRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void stopRecordProcess() throws IOException {
        if (recorderProcess != null) {
            recorderProcess.destroy();
            if (lastRecordPIDNumber > 0) {
                Runtime runtime = Runtime.getRuntime();
                Object recorder = AppProperties.getProperties().get(RECORDER_PROPERTY);
                Process recorders = runtime.exec("killall " + recorder);
                try {
                    recorders.waitFor();
                } catch (InterruptedException ex) {
                    Logger.getLogger(VoiceRecorder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            recorderProcess = null;
        }
    }

    public static void disableAutoRecording() throws IOException {
        stopRecording();
        enabled = false;
    }

    public static void enableAutoRecording() throws IOException {
        enabled = true;
    }

    public static void toggleAutoRecording() throws IOException {
        if (enabled) {
            disableAutoRecording();
        } else {
            enableAutoRecording();
        }
    }

    public static void startRecording(Customer customer, String phone) throws IOException {
        if (enabled) {
            if (recording) {
                stopRecording();
            }
            recording = true;
            lauchRecordProcess(getOutputFile(customer, phone));
        }
    }

    public static void stopRecording() throws IOException {
        if (enabled && recording) {
            recording = false;
            stopRecordProcess();
            System.out.println("should stop recording");
        }
    }

    private static File getOutputFile(Customer customer, String phone) throws IOException {
        String outputPath = AppProperties.getProperties().getProperty(OUTPUT_PATH_PROPERTY);
        Calendar calendar = new GregorianCalendar();
        File baseDirectory = new File(outputPath);
        String targetName = calendar.get(Calendar.DAY_OF_MONTH) + "_" + (calendar.get(Calendar.MONTH) + 1) + "_" + calendar.get(Calendar.YEAR);
        File targetDirectory = new File(baseDirectory, targetName);
        if (!targetDirectory.exists() && !targetDirectory.mkdirs()) {
            throw new FileNotFoundException("Cannot get the output file.");
        }
        String customerName = "";
        if (customer != null) {
            customerName = customer.getName().toLowerCase().replace(" ", "");
        }
        String fileName = calendar.get(Calendar.HOUR) + "_" + calendar.get(Calendar.MINUTE) + "_" + calendar.get(Calendar.SECOND) + "_" + customerName + "_" + phone + ".wav";
        File targetFile = new File(targetDirectory, fileName);
        targetFile.createNewFile();
        return targetFile;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static boolean isRecording() {
        return recording;
    }
}
