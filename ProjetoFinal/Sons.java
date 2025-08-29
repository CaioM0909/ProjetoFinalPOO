package ProjetoFinal;

import javax.sound.sampled.*;
import java.io.File;
import javax.swing.JOptionPane;

public class Sons {
    private String wav;
    Sons(String caminho){
        wav = caminho;
    }
    public  void playMusic() {
        try {
            File file = new File(wav);
            if (file.exists()) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } else {
                System.out.println("Arquivo de audio nao encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
