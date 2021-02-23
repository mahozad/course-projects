import javax.sound.sampled.*;
import javax.swing.*;
import java.time.*;
import java.io.*;

class Beeper implements Runnable {

    private Clip[] clips = new Clip[2];
    private MainFrame frame;

    Beeper(MainFrame mainFrame) {
        frame = mainFrame;
        initializeSounds();
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (Beeper.class) {
                    while (frame.isMuted()) {
                        Beeper.class.wait();
                    }
                }
                if (LocalTime.now().getSecond() == 0) {
                    if (frame.mustHalfBeep() && LocalTime.now().getMinute() == 30) {
                        play(0);
                    } else if (LocalTime.now().getMinute() == 0) {
                        play(1);
                    }
                }
                Thread.sleep(800);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void initializeSounds() {
        try {
            File halfBeep = new File("res\\Half_Beep.wav");
            File fullBeep = new File("res\\Full_Beep.wav");
            AudioInputStream audioIn1 = AudioSystem.getAudioInputStream(halfBeep
                    .toURI().toURL());
            AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(fullBeep
                    .toURI().toURL());
            clips[0] = AudioSystem.getClip();
            clips[0].open(audioIn1);
            clips[1] = AudioSystem.getClip();
            clips[1].open(audioIn2);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error while initializing sounds");
        }
    }

    private void play(int clipNumber) throws InterruptedException {
        clips[clipNumber].setFramePosition(0);
        clips[clipNumber].start();
        Thread.sleep(600);
    }
}
