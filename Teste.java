package ProjetoFinal;

import java.awt.*;
//redimensionamento da imagem;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.FileNotFoundException; // pro Scanner
import java.util.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;

import java.net.URL;  //esse é para o gif

public class Teste extends JFrame implements ActionListener {
    private JButton botao;
    private JLabel label;
    private BufferedImage imagem;
    private JLayeredPane janelas;
    private Jogador player1;
    private Rectangle areaUtil;
    private int larguraMax;
    private int comprimentoMax;

    public Teste(){
        super("Space Invaders");
   
        //Vai dar a área maxima da janela
        areaUtil = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        larguraMax = areaUtil.width;
        comprimentoMax =areaUtil.height;
        System.out.println(larguraMax);
        System.out.println(comprimentoMax);
        //setSize(larguraMax,comprimentoMax);
        setMinimumSize(new Dimension(larguraMax,comprimentoMax));
        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); //Centraliza a janela
        //janelas.setPreferredSize(new Dimension(1280, 760));


        //Criando o botao
        botao= new JButton("Jogar");
        botao.setFont(new Font("Arial", Font.BOLD, 80));
        botao.setForeground(new Color(45, 182, 250, 128));
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.addActionListener(this);
        botao.setFocusPainted(false); 
        botao.setBounds((int)(larguraMax/2.5),(int)(comprimentoMax/2.5), 300, 120);

        label= new JLabel();
        label.setBounds(0, 0, larguraMax, comprimentoMax);

        //Dimensionando a janela
        janelas = new JLayeredPane();
        janelas.setPreferredSize(new Dimension(larguraMax, comprimentoMax));


        //Captando a iamgem de fundo
        try{
            imagem= ImageIO.read(new File("ProjetoFinal/Imagens/backgroundEstrelas.png"));
        }catch(IOException e){
            System.out.println("Paia");
        }
        Image imagemCarregada = imagem.getScaledInstance(getWidth(),getHeight(),Image.SCALE_SMOOTH);
        ImageIcon iconeDeImg = new ImageIcon(imagemCarregada);
        label.setIcon(iconeDeImg);



        //add(label);
        //Adicionando o Botao
        label.add(botao);


        janelas.add(label, JLayeredPane.DEFAULT_LAYER);


        setContentPane(janelas);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redimensiona();
            }
        });

    }
    
   

    @Override
    public  void actionPerformed(ActionEvent eve){
        botao.setVisible(false);
        botao.setEnabled(false);
        player1= new Jogador(larguraMax, comprimentoMax, janelas);
        JLabel joga1 = player1.getNave();

        janelas.add(joga1, JLayeredPane.PALETTE_LAYER);
        
        recriaFileiras();
        

        //possibilitando a movimentação
        this.addKeyListener(player1);
        this.setFocusable(true); // vai permitir o foco na janela
        this.requestFocusInWindow(); //foca na janela
    }


    public void recriaFileiras(){
        int numAliens;
        int x=200, y1=0,y2=100;
        numAliens =larguraMax/150;
        Alien1 [] aliens1 = new Alien1[numAliens];
        Alien2 [] aliens2 = new Alien2[numAliens];
        for(int i=0;i<numAliens;i++){
            aliens1[i]= new Alien1(x,y1,janelas);
            aliens2[i]= new Alien2(x,y2,janelas);
            JLabel novoAlien1= aliens1[i].getAlien1();
            JLabel novoAlien2= aliens2[i].getAlien2();
            janelas.add(novoAlien1, JLayeredPane.PALETTE_LAYER);
            janelas.add(novoAlien2, JLayeredPane.PALETTE_LAYER);
            x+=120;
        }
                 
    }


    //Isso é para redimensionar as coisas na tela
    public void redimensiona(){
        if(imagem!=null && player1!=null && botao!=null){
            Image imagemCarregada = imagem.getScaledInstance(getWidth(),getHeight(),Image.SCALE_SMOOTH);
            ImageIcon iconeDeImg = new ImageIcon(imagemCarregada);
            label.setIcon(iconeDeImg);
            label.setBounds(0, 0, getWidth(), getHeight());
            int largura = getWidth()/2;
            int comprimento = getHeight()/2;
            botao.setBounds(largura-(largura/5),comprimento-(comprimento/5),300,120);
        }
    }
    //Aqui é o main para executar tudo
    public static void main(String [] args){
        Teste visualiza = new Teste();
        visualiza.setVisible(true);
    }

}

class Jogador implements KeyListener{
    private JLayeredPane camada;
    private URL url;
    private JLabel nave;
    private int posicaoX;
    private int posicaoY;
    private Timer mover;
    private int direita=0, esquerda=0;
    private int xMax, yMax;
    private JLabel tiro;
    private long ultimoTiro=0;
        Jogador(int w, int h, JLayeredPane camadas){
            camada=camadas;
            xMax=w;
            yMax=h;
            url = Jogador.class.getResource("/ProjetoFinal/Imagens/gifNave.gif");
            ImageIcon icon = new ImageIcon(url);
            nave = new JLabel(icon);
            posicaoX=(int)(xMax/2.5);
            posicaoY=yMax-240;
            nave.setBounds(posicaoX, posicaoY, 250, 250);
            mover = new Timer(10, e-> mexer() );
            mover.start();
        }
        public JLabel getNave(){
            return nave;
        }

        public void setPosicao(int w,int h){
            posicaoX=w;
            posicaoY=h;
            nave.setBounds(w, h, 250, 250);
        }

        @Override 
        public void keyPressed(KeyEvent aperta){
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
                    Tiro novoTiro= new Tiro(posicaoX,posicaoY, camada);
                    tiro=novoTiro.getTiro();
                    camada.add(tiro, JLayeredPane.PALETTE_LAYER);
                    ultimoTiro = atual;    
                }
                
                break;
            }
        }
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

}

class Tiro{
    private URL url;
    private JLabel tiro;
    private int posicaoX, posicaoY;
    private Timer mover;
    private JLayeredPane camada;
        Tiro(int x, int y, JLayeredPane camadas){
            posicaoX=x;
            posicaoY=y;
            camada=camadas;
            url = Tiro.class.getResource("/ProjetoFinal/Imagens/tiroNave.gif");
            ImageIcon icon = new ImageIcon(url);
            tiro = new JLabel(icon);
            tiro.setBounds(posicaoX, posicaoY-30, 250, 250);
            mover = new Timer(10, e-> mexer() );
            mover.start();
        }
        public void mexer(){
            posicaoY-=6;
            tiro.setBounds(posicaoX, posicaoY-30, 250, 250);
            for (Component comp : camada.getComponents()){
                if (comp != tiro && "alien".equals(comp.getName()) && (posicaoY-(comp.getY()+50)<=2)&& (posicaoX>=comp.getX()-50) && (posicaoX<=comp.getX()+50)){
                    
                    System.out.println(comp);
                    camada.remove(comp); // remove o inimigo
                    camada.remove(tiro); // remove o tiro
                    camada.repaint();    // força a atualização da tela
                    mover.stop();
                    return;
                }
            }

            if(posicaoY<=-100){
                camada.remove(tiro);
            }
        }
        public JLabel getTiro(){
            return tiro;
        }
}

class Alien1{
    private URL url;
    private JLabel novoAlien;
    private JLayeredPane camada;
    Alien1(int x, int y, JLayeredPane camadas){
        camada=camadas;
        url = Alien1.class.getResource("/ProjetoFinal/Imagens/alien1.gif");
        ImageIcon icon = new ImageIcon(url);
        novoAlien = new JLabel(icon);
        novoAlien.setName("alien");
        novoAlien.setBounds(x, y, 250, 250);
    }
    public JLabel getAlien1(){
        return novoAlien;
    }
}
class Alien2{
    private URL url;
    private JLabel novoAlien;
    private JLayeredPane camada;
    Alien2(int x, int y, JLayeredPane camadas){
        camada=camadas;
        url = Alien1.class.getResource("/ProjetoFinal/Imagens/alien2.gif");
        ImageIcon icon = new ImageIcon(url);
        novoAlien = new JLabel(icon);
        novoAlien.setName("alien");
        novoAlien.setBounds(x, y, 250, 250);
    }
    public JLabel getAlien2(){
        return novoAlien;
    }
}