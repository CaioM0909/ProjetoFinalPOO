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
//Classe para a criação do jogador
class Jogador implements KeyListener{
    private JLayeredPane camada;
    private URL url;
    private JLabel nave;
    private int posicaoX;
    private int posicaoY;
    private Timer mover;
    private int direita=0, esquerda=0;
    private int xMax, yMax;
    private int vidas=3;
    private JLabel tiro;
    private long ultimoTiro=0;
    private List<Alien1> lista1;
    private List<Alien2> lista2;
    private List<Alien3> lista3;
    private List<Alien1> lista4;
    private List<Alien2> lista5;
    private List<Jogador> jogador;
    //parte dos corações
    private URL caminhoCoracao;
    private JLabel coracao1, coracao2, coracao3;

    private Jogo janela;

    private int derrota=0;
    //Irá criar o jogador definir suas vidas e começar a permitir a movimentação dele
        Jogador(int w, int h, JLayeredPane camadas, List<Alien1> listaAliens1,  List<Alien1> listaAliens1_2, List<Alien2> listaAliens2, List<Alien2> listaAliens2_2,List<Alien3> listaAliens3, Jogo janelas, List<Jogador> player){
            jogador=player;
            lista1= listaAliens1;
            lista2= listaAliens2;
            lista3= listaAliens3;
            lista4= listaAliens1_2;
            lista5= listaAliens2_2;
            camada=camadas;
            janela=janelas;
            xMax=w;
            yMax=h;
            url = Jogador.class.getResource("/ProjetoFinal/Imagens/gifNave.gif");
            ImageIcon icon = new ImageIcon(url);
            nave = new JLabel(icon);
            nave.setName("nave");
            posicaoX=(int)(xMax/2.5);
            posicaoY=yMax-240;
            nave.setBounds(posicaoX, posicaoY, 250, 250);
            mover = new Timer(10, e-> mexer() );
            mover.start();
            criaCoracoes();
        }
        public int getYNave(){
            return posicaoY;
        }
        public int getXNave(){
            return posicaoX;
        }
        public JLabel getNave(){
            return nave;
        }
        //Deinie a posição que le irá começar
        public void setPosicao(int w,int h){
            posicaoX=w;
            posicaoY=h;
            nave.setBounds(w, h, 250, 250);
        }
        //Permite que ao clicar D vá para a direita e ao pertar A vá para esquerda além de ao apertar ESPAÇO realize a criação de um tiro
        @Override 
        public void keyPressed(KeyEvent aperta){
            if(derrota!=1){
                int tecla = aperta.getKeyCode();
                switch(tecla){

                    case KeyEvent.VK_D:
                    direita=1;
                    break;

                    case KeyEvent.VK_A:
                    esquerda=1;
                    break;

                    case KeyEvent.VK_SPACE:
                    long atual = System.currentTimeMillis();
                    //Vai dar um cooldown entre os tiros
                    if (atual - ultimoTiro >= 300) {
                        Tiro novoTiro= new Tiro(posicaoX,posicaoY, camada,lista1,lista4,lista2,lista5,lista3,janela,jogador);
                        tiro=novoTiro.getTiro();
                        camada.add(tiro, JLayeredPane.PALETTE_LAYER);
                        ultimoTiro = atual;    
                    }
                    
                    break;
                }
            }
        }
        //Irá para a movimentação da nave ao soltar as teclas D ou A
        @Override public void keyReleased(KeyEvent solta) {
            int soltou= solta.getKeyCode();
            switch(soltou){
                case KeyEvent.VK_D:
                direita=0;
                break;
                case KeyEvent.VK_A:
                esquerda=0;
                break;
            }
        }
        @Override public void keyTyped(KeyEvent e) {}

        public void mexer(){
            //Vai mover a nave para os dois lados
            if(posicaoX>=-80 && posicaoX<=xMax-170){
                //Impede de sair da tele pela direita
                if(direita==1){
                    posicaoX+=8;
                    if(posicaoX>xMax-170){
                        posicaoX=xMax-170;
                    }
                }
                //Impede de sair da tela pela esquerda
                if(esquerda==1){
                    posicaoX-=8;
                    if(posicaoX<-80){
                        posicaoX=-80;
                    }
                }

                setPosicao(posicaoX, posicaoY);
            }
            
        }
        public void criaCoracoes(){
            caminhoCoracao = Jogador.class.getResource("/ProjetoFinal/Imagens/coracao.gif");
            ImageIcon icone = new ImageIcon(caminhoCoracao);
            coracao1 = new JLabel(icone);
            coracao2 = new JLabel(icone);
            coracao3 = new JLabel(icone);

            coracao1.setBounds((int)(xMax/1.15),-30,100,100);
            camada.add(coracao1,JLayeredPane.PALETTE_LAYER);
            coracao2.setBounds((int)(xMax/1.165),-30,100,100);
            camada.add(coracao2,JLayeredPane.PALETTE_LAYER);
            coracao3.setBounds((int)(xMax/1.18),-30,100,100);
            camada.add(coracao3,JLayeredPane.PALETTE_LAYER);
            
        }
        public int getVidas(){
            return vidas;
        }
        public void setVidas(int num){
            if(num==2){
                camada.remove(coracao1);
            }
            if(num==1){
                camada.remove(coracao2);
            }
            if(num==0){
                camada.remove(coracao3);
            }
            vidas= num;
        }

        public void setDerrota(int setar){
            derrota=setar;
        }

}
