package audiorecord;

import helper.AppProperties;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoiceRecorderFactory {

    private static final String RECORDER_METHOD_PROPERTY = "recordingusing";
    private static VoiceRecorder instance;

    public static VoiceRecorder getInstance() {

        String using = null;
        try {
            using = AppProperties.getProperty(RECORDER_METHOD_PROPERTY);
        } catch (IOException ex) {
            Logger.getLogger(VoiceRecorderFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (instance == null) {
            if (using != null && using.startsWith("process")) {
                instance = new VoiceRecorderArecordImp();
            } else {
                instance = new VoiceRecorderAudioImp();
            }
        }
        return instance;
    }
}
