package audiorecord;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoiceRecorderAudioImp extends VoiceRecorder {

    private Thread recorderThread;

    @Override
    protected void startRecordingImp(final File outputFile) throws IOException {
        recorderThread = new Thread() {

            @Override
            public void run() {
                try {
                    AudioRecorder.start(outputFile);
                } catch (AudioRecorderException ex) {
                    Logger.getLogger(VoiceRecorderAudioImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        recorderThread.start();
    }

    @Override
    protected void stopRecordingImp() throws IOException {
        AudioRecorder.finish();
    }

}
