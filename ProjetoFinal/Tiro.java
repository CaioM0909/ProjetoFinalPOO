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

class Tiro{
    private URL url;
    private JLabel tiro;
    private int posicaoX, posicaoY;
    private Timer mover;
    private JLayeredPane camada;
    private List<Alien1> lista1;
    private List<Alien1> lista1_2;
    private List<Alien2> lista2;
    private List<Alien2> lista2_2;
    private List<Alien3> lista3;
    private List<Jogador> vivo;
    private static int ativar=1;
    private Teste janela;
    private static int vidasNave=3;
        Tiro(int x, int y, JLayeredPane camadas,List<Alien1> listaAliens1,List<Alien1> listaAliens1_2,List<Alien2> listaAliens2, List<Alien2> listaAliens2_2,List<Alien3> listaAliens3, Teste janelas, List<Jogador> player){
            vivo=player;
            lista1=listaAliens1;
            lista1_2=listaAliens1_2;
            lista2= listaAliens2;
            lista2_2= listaAliens2_2;
            lista3= listaAliens3;
            janela=janelas;
            posicaoX=x;
            posicaoY=y;
            camada=camadas;
            if(vivo.isEmpty()){
                ativar=0;
            }
            else{
                ativar=1;
                url = Tiro.class.getResource("/ProjetoFinal/Imagens/tiroNave.gif");
                ImageIcon icon = new ImageIcon(url);
                tiro = new JLabel(icon);
                tiro.setBounds(posicaoX, posicaoY-30, 250, 250);
                mover = new Timer(10, e-> mexer() );
                mover.start();
            }
        }
        public void mexer(){
            posicaoY-=6;
            tiro.setBounds(posicaoX, posicaoY-30, 250, 250);
            for (Component comp : camada.getComponents()){
                if (comp != tiro && "alien".equals(comp.getName()) && (posicaoY-(comp.getY()+50)<=2)&& (posicaoX>=comp.getX()-50) && (posicaoX<=comp.getX()+50)){
                    Integer id = (Integer)((JComponent) comp).getClientProperty("id");
                    String tipo= (String)((JComponent) comp).getClientProperty("tipo");
                    lista1.removeIf(a -> a.getId() == id);
                    if("alien1".equals(tipo)){
                        janela.setScore(50);
                    }
                    if("alien2".equals(tipo)){
                        janela.setScore(100);
                    }
                    if("alien3".equals(tipo)){
                        janela.setScore(200);
                    }
                    if("naveAlien".equals(tipo)){
                        janela.setScore(1000);
                    }
                    lista1_2.removeIf(a -> a.getId() == id);
                    lista2.removeIf(a -> a.getId() == id);
                    lista2_2.removeIf(a -> a.getId() == id);
                    lista3.removeIf(a -> a.getId() == id);
                    //esse é para remover a nave de cima
                    if(id==-1){
                        vidasNave--;
                        if(vidasNave==0){
                            camada.remove(comp); // remove o inimigo
                            vidasNave=3;
                        }
                    }
                    //esse remove os aliens
                    if(id!=-1){
                        camada.remove(comp); // remove o inimigo
                    }
                    camada.remove(tiro); // remove o tiro
                    camada.repaint();    // força a atualização da tela
                    mover.stop();

                    if(lista1.isEmpty() && lista1_2.isEmpty() && lista2.isEmpty() && lista2_2.isEmpty() && lista3.isEmpty()){
                        janela.recriaFileiras();
                    }


                    return;
                }
            }

            if(posicaoY<=-100){
                camada.remove(tiro);
                camada.repaint();
                mover.stop();
                return;
            }
        }
        public JLabel getTiro(){
            return tiro;
        }
}
