package ProjetoFinal;

import java.awt.*;
//redimensionamento da imagem;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.FileNotFoundException; // pro Scanner
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;

import java.net.URL;  //esse é para o gif

public class Teste extends JFrame implements ActionListener {
    private JTextArea highScore;
    private JTextArea scorePartida;
    private int scoreAnterior=0, scoreAtual=0;
    private String score="";
    private String novoScorePartida="";
    private JButton botao;
    private JLabel label;
    private JLabel title;
    private BufferedImage imagem;
    private BufferedImage titulo;
    private JLayeredPane janelas;
    private Jogador player1;
    private Rectangle areaUtil;
    private int larguraMax;
    private int comprimentoMax;
    private java.util.List<Jogador> jogador = new ArrayList<>();
    private java.util.List<Alien1> listaAliens1 = new ArrayList<>();
    private java.util.List<Alien1> listaAliens1_2 = new ArrayList<>();
    private java.util.List<Alien2> listaAliens2 = new ArrayList<>();
    private java.util.List<Alien2> listaAliens2_2 = new ArrayList<>();
    private java.util.List<Alien3> listaAliens3 = new ArrayList<>();
    private Timer moverAliens;
    private Timer naveCooldown;
    private int direcao = 1;
    private int identificadores=0;
    private int yJogador;
    private int derrota=0;

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

         //Criando o highScore
        score="HIGH SCORE:"+scoreAnterior;
        highScore= new JTextArea(score);
        highScore.setFont(new Font("Arial", Font.BOLD, 40));
        highScore.setBounds((int)(larguraMax/2.6),(int)(comprimentoMax/1.5), 1000, 300);
        highScore.setForeground(new Color(255, 255, 255, 128));
        highScore.setOpaque(false);
        janelas.add(highScore,JLayeredPane.DEFAULT_LAYER);
        //Criando o score atual
        novoScorePartida="SCORE:"+scoreAtual;
        scorePartida= new JTextArea(novoScorePartida);
        scorePartida.setFont(new Font("Arial", Font.BOLD, 16));
        scorePartida.setBounds((int)(larguraMax/1.1),0, 200, 200);
        scorePartida.setForeground(new Color(255, 255, 255, 128));
        scorePartida.setOpaque(false);
        scorePartida.setVisible(false);
        janelas.add(scorePartida,JLayeredPane.DEFAULT_LAYER);

        //Captando a iamgem de fundo
        try{
            imagem= ImageIO.read(new File("ProjetoFinal/Imagens/backgroundEstrelas.png"));
            titulo= ImageIO.read(new File("ProjetoFinal/Imagens/Cosmic_Invasion.png"));
        }catch(IOException e){
            System.out.println("Paia");
        }
        Image imagemCarregada = imagem.getScaledInstance(getWidth(),getHeight(),Image.SCALE_SMOOTH);
        ImageIcon iconeDeImg = new ImageIcon(imagemCarregada);
        Image tituloRedi = titulo.getScaledInstance(getWidth()/2,getHeight()/2,Image.SCALE_SMOOTH);
        ImageIcon tituloDeImg = new ImageIcon(tituloRedi);
        title = new JLabel(tituloDeImg);
        title.setBounds((larguraMax-getWidth()/2)/2,(comprimentoMax-getWidth()/2)/2,getWidth()/2,getHeight()/2);
        janelas.add(title,JLayeredPane.PALETTE_LAYER);
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
    
    public int getDerrota(){
        return derrota;
    }
   

    @Override
    public  void actionPerformed(ActionEvent eve){
        derrota=0;
        botao.setVisible(false);
        botao.setEnabled(false);
        title.setVisible(false);
        highScore.setVisible(false);
        scorePartida.setVisible(true);
        player1= new Jogador(larguraMax, comprimentoMax, janelas,listaAliens1, listaAliens1_2,listaAliens2, listaAliens2_2,listaAliens3, this,jogador);
        jogador.add(player1);
        yJogador= player1.getYNave();
        JLabel joga1 = player1.getNave();

        janelas.add(joga1, JLayeredPane.PALETTE_LAYER);
        
        recriaFileiras();

        moverAliens = new Timer(2000, e -> moveAliens());
        moverAliens.start();
        naveCooldown= new Timer(60000,e ->spawnaNaveAlien());   
        naveCooldown.start();
        

        //possibilitando a movimentação
        this.addKeyListener(player1);
        this.setFocusable(true); // vai permitir o foco na janela
        this.requestFocusInWindow(); //foca na janela
    }


    public void recriaFileiras(){
        int numAliens;
        int x=200, y1=380, y2=310, y3=240, y4=170, y5=100;
        numAliens =larguraMax/150;
        for(int i=0;i<numAliens;i++){
            Alien1 alien1= new Alien1(x,y1);
            alien1.setId(identificadores);
            identificadores++;

            Alien1 alien1_2= new Alien1(x,y2);
            alien1_2.setId(identificadores);
            identificadores++;

            Alien2 alien2= new Alien2(x,y3);
            alien2.setId(identificadores);
            identificadores++;

            Alien2 alien2_2= new Alien2(x,y4);
            alien2_2.setId(identificadores);
            identificadores++;

            Alien3 alien3= new Alien3(x,y5);
            alien3.setId(identificadores);
            identificadores++;

            JLabel novoAlien1= alien1.getAlien1();
            novoAlien1.putClientProperty("id",alien1.getId());

            JLabel novoAlien1_2= alien1_2.getAlien1();
            novoAlien1_2.putClientProperty("id",alien1_2.getId());

            JLabel novoAlien2= alien2.getAlien2();
            novoAlien2.putClientProperty("id",alien2.getId());

            JLabel novoAlien2_2= alien2_2.getAlien2();
            novoAlien2_2.putClientProperty("id",alien2_2.getId());

            JLabel novoAlien3= alien3.getAlien3();
            novoAlien3.putClientProperty("id",alien3.getId());

            listaAliens1.add(alien1);
            listaAliens1_2.add(alien1_2);
            listaAliens2.add(alien2);
            listaAliens2_2.add(alien2_2);
            listaAliens3.add(alien3);

            janelas.add(novoAlien1, JLayeredPane.PALETTE_LAYER);
            janelas.add(novoAlien1_2, JLayeredPane.PALETTE_LAYER);
            janelas.add(novoAlien2, JLayeredPane.PALETTE_LAYER);
            janelas.add(novoAlien2_2, JLayeredPane.PALETTE_LAYER);
            janelas.add(novoAlien3, JLayeredPane.PALETTE_LAYER);

            x+=120;
        }
    }

    public void spawnaNaveAlien(){
        NaveAlien naveAlien = new NaveAlien(larguraMax,janelas);
        JLabel novaNave = naveAlien.getNaveAlien();
        novaNave.putClientProperty("id",-1);
        janelas.add(novaNave, JLayeredPane.PALETTE_LAYER);
    }

    public void moveAliens(){
        int bateu=0;
        for(Alien1 a1 : listaAliens1){
            a1.movimenta(direcao);
            if (a1.getX() <= 0 || a1.getX() >= larguraMax - 200) {
                bateu=1;
            }
            if(a1.getY()>=yJogador-120){
                derrota=1;
            }
        }
        for(Alien1 a1_2 : listaAliens1_2){
            a1_2.movimenta(direcao);
            if (a1_2.getX() <= 0 || a1_2.getX() >= larguraMax - 120) {
                bateu=1;
            }
            if(a1_2.getY()>=yJogador-200){
                derrota=1;
            }
        }
        for(Alien2 a2 : listaAliens2){
            a2.movimenta(direcao);
            if (a2.getX() <-120  || a2.getX() >= larguraMax - 120) {
                bateu=1;
            }
            if(a2.getY()>=yJogador-200){
                derrota=1;
            }
        }
        for(Alien2 a2_2 : listaAliens2_2){
            a2_2.movimenta(direcao);
            if (a2_2.getX() <-120  || a2_2.getX() >= larguraMax - 120) {
                bateu=1;
            }
            if(a2_2.getY()>=yJogador-120){
                derrota=1;
            }
        }
        for(Alien3 a3 : listaAliens3){
            a3.movimenta(direcao);
            if (a3.getX() <= 0 || a3.getX() >= larguraMax - 200) {
                bateu=1;
            }
            if(a3.getY()>=yJogador-120){
                derrota=1;
            }
        }
        



        if(bateu==1){
            javax.swing.Timer delay = new javax.swing.Timer(300, e -> {
                direcao *= -1;
                for(Alien1 a1 : listaAliens1){
                    a1.descer();
                }
                for(Alien1 a1_2 : listaAliens1_2){
                    a1_2.descer();
                }
                for(Alien2 a2 : listaAliens2){
                    a2.descer();
                }
                for(Alien2 a2_2 : listaAliens2_2){
                    a2_2.descer();
                }
                for(Alien3 a3 : listaAliens3){
                    a3.descer();
                }
            });
            delay.setRepeats(false);
            delay.start(); 
        }
        if(derrota==1){
            for (Component comp : janelas.getComponents()){
                if (comp != label && comp !=title && comp!=highScore && comp!=scorePartida) {
                    janelas.remove(comp);
                }
            }
            player1.setDerrota(1);
            jogador.remove(player1);
            janelas.repaint();
            naveCooldown.stop();
            moverAliens.stop();
            listaAliens1.clear();
            listaAliens1_2.clear();
            listaAliens2.clear();
            listaAliens2_2.clear();
            listaAliens3.clear();
            botao.setVisible(true);
            botao.setEnabled(true);
            title.setVisible(true);
            mudaHighScore();
            highScore.setVisible(true);
            scorePartida.setVisible(false);
        }
    }

    public void setScore(int num){
        scoreAtual+=num;
        scorePartida.setText("SCORE:"+scoreAtual);
    }

    public void mudaHighScore(){
        if(scoreAtual>=scoreAnterior){
            scoreAnterior=scoreAtual;
        }
        scoreAtual=0;
        highScore.setText("HIGH SCORE:" + scoreAnterior);
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

    