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
    public Teste(){
        super("Space Invaders");
        setSize(1280,760);
        setMinimumSize(new Dimension(1280,760));
        setLayout(null);
        //Criando o botao
        botao= new JButton("Jogar");
        botao.setFont(new Font("Arial", Font.BOLD, 80));
        botao.setForeground(Color.RED);
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.addActionListener(this);
        botao.setFocusPainted(false); 
        botao.setBounds(640, 380, 300, 120);

        label= new JLabel();
        label.setBounds(0, 0, 1280, 760);

        janelas = new JLayeredPane();
        janelas.setPreferredSize(new Dimension(1280, 760));


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
        player1= new Jogador();
        JLabel joga1 = player1.getNave();

        janelas.add(joga1, JLayeredPane.PALETTE_LAYER);

        //possibilitando a movimentação
        this.addKeyListener(player1);
        this.setFocusable(true); // vai permitir o foco na janela
        this.requestFocusInWindow(); //foca na janela
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
            player1.setPosicao(largura,comprimento+100);
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
    private URL url;
    private JLabel nave;
    private int posicaoX;
    private int posicaoY;
    private Timer mover;
    private int direita=0, esquerda=0;
    private int xMax, yMax;
        Jogador(){
            url = Jogador.class.getResource("/ProjetoFinal/Imagens/gifNave.gif");
            ImageIcon icon = new ImageIcon(url);
            nave = new JLabel(icon);
            posicaoX=600;
            posicaoY=500;
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

        public void setMax(int w, int h){
            xMax= w;
            yMax= h;
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
            if(posicaoX>=-55 && posicaoX<=2000){
                if(direita==1){
                    posicaoX+=5;
                    if(posicaoX>2000){
                        posicaoX=2000;
                    }
                }
                if(esquerda==1){
                    posicaoX-=5;
                    if(posicaoX<-55){
                        posicaoX=-55;
                    }
                }

                setPosicao(posicaoX, posicaoY);
            }
            
        }

}