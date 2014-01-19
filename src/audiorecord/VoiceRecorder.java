package audiorecord;

import entity.Customer;
import helper.AppProperties;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class VoiceRecorder {

    final private static String AUTO_RECORD_PROPERTY = "autorecord";
    final private static String OUTPUT_PATH_PROPERTY = "voicedir";
    private boolean enabled;
    private boolean recording = false;

    VoiceRecorder() {
        try {
            enabled = AppProperties.getProperty(AUTO_RECORD_PROPERTY).endsWith("true");
        } catch (IOException ex) {
            enabled = false;
            Logger.getLogger(VoiceRecorderArecordImp.class.getName()).log(Level.SEVERE, "Cannot determine auto record property.", ex);
        }
    }

    public void disableAutoRecording() throws IOException {
        stopRecording();
        AppProperties.setProperty(AUTO_RECORD_PROPERTY, "false");
        AppProperties.storeProperties();
        enabled = false;
    }

    public void enableAutoRecording() throws IOException {
        AppProperties.setProperty(AUTO_RECORD_PROPERTY, "true");
        AppProperties.storeProperties();
        enabled = true;
    }

    public void toggleAutoRecording() throws IOException {
        if (enabled) {
            disableAutoRecording();
        } else {
            enableAutoRecording();
        }
    }

    public void startRecording(Customer customer, String phone) throws IOException {
        if (enabled) {
            if (recording) {
                stopRecordingImp();
            }
            recording = true;
            startRecordingImp(getOutputFile(customer, phone));
        }
    }

    public void stopRecording() throws IOException {
        if (enabled && recording) {
            recording = false;
            stopRecordingImp();
        }
    }

    private File getOutputFile(Customer customer, String phone) throws IOException {
        String outputPath = AppProperties.getProperty(OUTPUT_PATH_PROPERTY);
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

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isRecording() {
        return recording;
    }

    protected abstract void startRecordingImp(File outputFile) throws IOException;

    protected abstract void stopRecordingImp() throws IOException;
}
