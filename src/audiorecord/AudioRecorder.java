package audiorecord;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioRecorder {

    private static final float SAMPLE_RATE = 8000;
    private static final int SAMPLE_SIZE_BITS = 8;
    private static final int CHANNELS = 2;

    private static final AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private static TargetDataLine line;
    private static final AudioFormat format;
    
    static {
        boolean signed = true;
        boolean bigEndian = true;
        format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_BITS, CHANNELS, signed, bigEndian);
    }

    static void start(File outputFile) throws AudioRecorderException {
        
        try {
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                throw new AudioRecorderException("This line is not supported.");
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.addLineListener(new LineListener() {

                @Override
                public void update(LineEvent event) {
                    
                }
            });
            line.start();
            AudioInputStream ais = new AudioInputStream(line);
            AudioSystem.write(ais, fileType, outputFile);
        } catch (LineUnavailableException | IOException ex) {
            throw new AudioRecorderException(ex.toString());
        }
    }

    static void finish() {
        line.stop();
        line.close();
    }
}
