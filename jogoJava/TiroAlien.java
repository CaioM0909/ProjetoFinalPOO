package jogoJava;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;
import java.net.URL;
//Classe para gerar o tiro dos aliens similar ao do tiro Padrão da Nave porem com algumas alterações
class TiroAlien{
    private URL url;
    private JLabel tiroAlien;
    private int posicaoX, posicaoY;
    private int yMax;
    private Timer mover;
    private JLayeredPane camada;
    private List<Jogador> vivo;
    private static int ativar=1;
    private Jogo janela;
    private static int vidasNave=3;
    private Random escolheTiro;
    private int escolhido;
    private int speed=0;
    private Jogador player1;
        TiroAlien(int x, int y, JLayeredPane camadas, Jogo janelas, List<Jogador> player, int yMaximo){
            player1 = player.get(0);
            vidasNave=player1.getVidas();
            escolheTiro= new Random();
            escolhido =escolheTiro.nextInt(20);
            vivo=player;
            janela=janelas;
            posicaoX=x;
            posicaoY=y;
            camada=camadas;
            yMax=yMaximo;
            if(vivo.isEmpty()){
                ativar=0;
            }
            //Aqui randomiza qual tiro será escolhido
            else{
                ativar=1;
                if(escolhido<=9){
                    url = Tiro.class.getResource("/Imagens/TiroRapido.gif");
                    speed=7;
                }
                if(escolhido>9 && escolhido<17){
                    url = Tiro.class.getResource("/Imagens/TiroMedio.gif");
                    speed=5;
                }
                if(escolhido>=17){
                    url = Tiro.class.getResource("/Imagens/TiroDevagar.gif");
                    speed=3;
                }
                if(url!=null){
                    ImageIcon icon = new ImageIcon(url);
                    tiroAlien = new JLabel(icon);
                }
                if(url==null){
                    System.out.println("Tiro alien nao encontrado");
                    tiroAlien = new JLabel("Imagem tiro alien");
                }
                tiroAlien.setBounds(posicaoX, posicaoY+30, 250, 250);
                mover = new Timer(10, e-> mexer());
                mover.start();
            }
        }
        //Função para mover o tiro
        public void mexer(){
            posicaoY+=speed;
            tiroAlien.setBounds(posicaoX, posicaoY+30, 250, 250);
            for (Component comp : camada.getComponents()){
                if (comp != tiroAlien && "nave".equals(comp.getName()) &&(posicaoY<=(comp.getY()+50)) &&((comp.getY()-50)-posicaoY<=2)&& (posicaoX>=comp.getX()-50) && (posicaoX<=comp.getX()+50)){
                    //Remove o tiro e diminui uma das vidas do jogador
                    camada.remove(tiroAlien);
                    camada.repaint();  
                    mover.stop();
                    vidasNave--;
                    player1.setVidas(vidasNave);
                    if(vidasNave==0){
                        janela.setDerrota();
                    }

                    return;
                }
            }
            //Caso o tiro saia da tela remove ele
            if(posicaoY>=yMax-50){
                camada.remove(tiroAlien);
                camada.repaint();
                mover.stop();
                return;
            }
        }
        public JLabel getTiro(){
            return tiroAlien;
        }
}

