package identicall;

import entity.Customer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoiceRecorder {

    final private static String AUTO_RECORD_PROPERTY = "autorecord";
    private static boolean enabled;
    private static boolean recording = false;

    static {
        try {
            enabled = AppProperties.getProperties().getProperty(AUTO_RECORD_PROPERTY).endsWith("true");
        } catch (IOException ex) {
            enabled = false;
            Logger.getLogger(VoiceRecorder.class.getName()).log(Level.SEVERE, "Cannot determine auto record property.", ex);
        }
    }
    
    public static void disableAutoRecording() throws IOException {
        System.out.println("disableAutoRecording");
        AppProperties.getProperties().setProperty(AUTO_RECORD_PROPERTY, "false");
        enabled = false;
    }
    
    public static void enableAutoRecording() throws IOException {
        System.out.println("enableAutoRecording");
        AppProperties.getProperties().setProperty(AUTO_RECORD_PROPERTY, "true");
        enabled = true;
    }
    
    public static void toggleAutoRecording() throws IOException {
        if (enabled) {
            disableAutoRecording();
        } else {
            enableAutoRecording();
        }
    }

    public static void startRecording(Customer customer, String phone) {
        if (enabled) {
            if (recording) {
                stopRecording();
            }
            recording = true;
            System.out.println("should record");
        }
    }

    public static void stopRecording() {
        if (enabled && recording) {
            recording = false;
            System.out.println("should stop recording");
        }
    }

    private static String getRecordingPath() {
        return "";
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static boolean isRecording() {
        return recording;
    }
}
