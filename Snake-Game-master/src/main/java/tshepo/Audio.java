package tshepo;

import javax.sound.sampled.*;

public class Audio {

    private Clip clip;

    public Audio(String s) {

        try {
            AudioInputStream Audio = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
            AudioFormat baseFormat = Audio.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(),
                    16, baseFormat.getChannels(),baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            AudioInputStream DAudio = AudioSystem.getAudioInputStream(decodeFormat, Audio);
            clip = AudioSystem.getClip();
            clip.open(DAudio);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void play() {
        clip.setFramePosition(0);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void levelUp() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop() {
        if(clip.isRunning()) clip.stop();
    }

    public void close() {
        stop();
        clip.close();

    }

}
