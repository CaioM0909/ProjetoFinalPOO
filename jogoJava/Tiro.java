package jogoJava;
import java.awt.*;

import java.util.List;

import javax.swing.*;
import javax.swing.Timer;

import java.net.URL;
//Classe para os tiros do jogador
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
    private Jogo janela;
    private static int vidasNave=3;
        Tiro(int x, int y, JLayeredPane camadas,List<Alien1> listaAliens1,List<Alien1> listaAliens1_2,List<Alien2> listaAliens2, List<Alien2> listaAliens2_2,List<Alien3> listaAliens3, Jogo janelas, List<Jogador> player){
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
            //Só permite caso o jogador esteja vivo
            if(vivo.isEmpty()){
                ativar=0;
            }
            //Cria o tiro
            else{
                ativar=1;
                url = Tiro.class.getResource("/Imagens/tiroNave.gif");
                if(url!=null){
                    ImageIcon icon = new ImageIcon(url);
                    tiro = new JLabel(icon);
                }
                if(url==null){
                    System.out.println("Imagem tiro nao encontrada");
                    tiro = new JLabel("Imagem tiro");
                }
                tiro.setBounds(posicaoX, posicaoY-30, 250, 250);
                tiro.setName("tiroJogador");
                mover = new Timer(10, e-> mexer() );
                mover.start();
            }
        }
        //Função para mover o tiro na tela
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
                    //Esse é para remover a nave de cima roxa
                    if(id==-1){
                        vidasNave--;
                        if(vidasNave==0){
                            camada.remove(comp);
                            vidasNave=3;
                        }
                    }
                    //Esse remove os aliens
                    if(id!=-1){
                        camada.remove(comp);
                    }
                    //Remove o tiro e para o movimento
                    camada.remove(tiro);
                    camada.repaint();   
                    mover.stop();
                    //Caso as listas de todos os aliens estejam vazias ele recria as fileiras de aliens
                    if(lista1.isEmpty() && lista1_2.isEmpty() && lista2.isEmpty() && lista2_2.isEmpty() && lista3.isEmpty()){
                        janela.recriaFileiras();
                    }


                    return;
                }
            }
            //Remove o tiro caso saia da tela
            if(posicaoY<=-100){
                camada.remove(tiro);
                camada.repaint();
                mover.stop();
                return;
            }
        }
        //Função para retornar o tiro para outras funções
        public JLabel getTiro(){
            return tiro;
        }
}
