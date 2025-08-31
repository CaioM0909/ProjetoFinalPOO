package jogoJava;

import javax.swing.*;
import javax.swing.Timer;

import java.net.URL;
//Classe para a naveAlien
class NaveAlien{
    private URL url;
    private JLabel novaNave;
    private int xAtual=0;
    private int xAdicional=5;
    private int xMax;
    private Timer mover;
    private JLayeredPane camada;
    private int vidas=3;
    //Cria a naveAlien
    NaveAlien(int xMaximo, JLayeredPane camadas){
        camada=camadas;
        xMax=xMaximo;
        url = Alien1.class.getResource("/Imagens/NaveAlien.gif");
        if(url!=null){
            ImageIcon icon = new ImageIcon(url);
            novaNave = new JLabel(icon);
        }
        if(url==null){
            novaNave = new JLabel("Imagem naveAlien");
        }
        novaNave.setName("alien");
        novaNave.putClientProperty("tipo", "naveAlien");
        novaNave.setBounds(0,-40, 250, 250);
        mover= new Timer(30, e-> movimenta());
        mover.start();
    }
    //Funçao que faz com que a nave alien se mova para direita e ao bater na parede da direita volte
    public void movimenta(){
        if(xAtual>xMax-200){
            xAdicional*=-1;
        }
        xAtual+=xAdicional;
        //Caso ela saia da tela pela esquerda remove
        if(xAtual<-200){
            camada.remove(novaNave);
            camada.repaint();
            mover.stop();
            return;
        }
        novaNave.setBounds(xAtual, -40, 250, 250);
    }
    //Define a vida da nave roxa
    public void setVidas(int num){
        vidas=num;
    }
    //Retorna a vida da nave roxa para outras funções
    public int getVidas(){
        return vidas;
    }
    public JLabel getNaveAlien(){
        return novaNave;
    }
}
