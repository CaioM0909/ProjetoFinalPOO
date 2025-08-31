package ProjetoFinal;

import javax.sound.sampled.*;
import java.io.File;
import javax.swing.JOptionPane;

public class Sons {
    private Clip clip;
    private String wav;
    Sons(String caminho){
        wav = caminho;
    }
    public  void play() {
        try {
            File file = new File(wav);
            if (file.exists()) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } else {
                System.out.println("Arquivo de audio nao encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stop(){
        clip.stop();
        clip.close();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
