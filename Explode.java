package ProjetoFinal;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;
import java.net.URL;
public class Explode {
    private Jogador jogador;
    private JLabel explosao;
    private int x,y;
    private URL url;
    private JLayeredPane janela;
    Explode(List<Jogador> player, JLayeredPane janelas){
        janela=janelas;
        jogador=player.get(0);
        x=jogador.getXNave();
        y=jogador.getYNave();
        url = Jogador.class.getResource("/ProjetoFinal/Imagens/explosaoGif.gif");
            ImageIcon icon = new ImageIcon(url);
            explosao = new JLabel(icon);
            explosao.setBounds(x, y, 250, 250);
            janela.add(explosao,JLayeredPane.PALETTE_LAYER);
    }
}
