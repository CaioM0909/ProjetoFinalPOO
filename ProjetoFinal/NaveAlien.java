package ProjetoFinal;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;

import java.net.URL;

class NaveAlien{
    private URL url;
    private JLabel novaNave;
    private int xAtual=0;
    private int xAdicional=5;
    private int xMax;
    private Timer mover;
    private JLayeredPane camada;
    private int vidas=3;
    NaveAlien(int xMaximo, JLayeredPane camadas){
        camada=camadas;
        xMax=xMaximo;
        url = Alien1.class.getResource("Imagens/NaveAlien.gif");
        ImageIcon icon = new ImageIcon(url);
        novaNave = new JLabel(icon);
        novaNave.setName("alien");
        novaNave.putClientProperty("tipo", "naveAlien");
        novaNave.setBounds(0,-40, 250, 250);
        mover= new Timer(30, e-> movimenta());
        mover.start();
    }
    public void movimenta(){
        if(xAtual>xMax-200){
            xAdicional*=-1;
        }
        xAtual+=xAdicional;
        if(xAtual<-200){
            camada.remove(novaNave);
            camada.repaint();
            mover.stop();
            return;
        }
        novaNave.setBounds(xAtual, -40, 250, 250);
    }
    public void setVidas(int num){
        vidas=num;
    }
    public int getVidas(){
        return vidas;
    }
    public JLabel getNaveAlien(){
        return novaNave;
    }
}
