package jogoJava;

import java.awt.*;
//redimensionamento da imagem;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;

import java.net.URL;  //esse é para o gifimport java.util.Random;

public class Jogo extends JFrame implements ActionListener {
    private JTextArea highScore;
    private JTextArea scorePartida;
    private JLabel gameOver;
    private JLabel alienTitulo;
    private JLabel playerTitulo;
    private int scoreAnterior=0, scoreAtual=0;
    private String score="";
    private String novoScorePartida="";
    private JButton botao;
    private JButton menu;
    private JButton muta;
    private ImageIcon imagemSom;
    private JLabel label;
    private JLabel title;
    private JLabel tutorial;
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
    private Timer moverAliens, naveCooldown, delayTiroAlien;
    private int direcao = 1;
    private int som=0;
    private int identificadores=0;
    private int yJogador;
    private int derrota=0;
    private Sons backgroundMusic;
    private Sons gameOverSound;
    private URL urlNaveTitulo;
    private URL urlJogadorTitulo;

    public Jogo(){
        super("Space Invaders");
   
        //Vai dar a área maxima da janela
        areaUtil = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        larguraMax = areaUtil.width;
        comprimentoMax =areaUtil.height;
        System.out.println("Largura: "+larguraMax);
        System.out.println("Comprimento: "+comprimentoMax);
        setMinimumSize(new Dimension(larguraMax,comprimentoMax));
        setMaximumSize(new Dimension(larguraMax,comprimentoMax));
        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); //Centraliza a janela

       

        //Criando o botao de jogar
        botao= new JButton("Jogar");
        botao.setFont(new Font("Arial", Font.BOLD, 80));
        botao.setForeground(new Color(45, 182, 250, 128));
        botao.setOpaque(false);
        botao.setContentAreaFilled(false);
        botao.setBorderPainted(false);
        botao.addActionListener(this);
        botao.setFocusPainted(false); 
        botao.setBounds((int)(larguraMax/2.5),(int)(comprimentoMax/2.5), 300, 120);

        //Criando o botao de menu
        menu= new JButton("Menu");
        menu.setFont(new Font("Arial", Font.BOLD, 80));
        menu.setForeground(new Color(45, 182, 250, 128));
        menu.setOpaque(false);
        menu.setContentAreaFilled(false);
        menu.setBorderPainted(false);
        menu.addActionListener(this);
        menu.setFocusPainted(false); 
        menu.setBounds((int)(larguraMax/2.5),(int)(comprimentoMax/2), 300, 120);
        menu.setVisible(false);
        menu.setEnabled(false);

    

        //Criando o botao de som
        imagemSom= new ImageIcon("Imagens/mute.png");
        muta= new JButton(imagemSom);
        muta.setOpaque(false);
        muta.setContentAreaFilled(false);
        muta.setBorderPainted(false);
        muta.addActionListener(this);
        muta.setFocusPainted(false); 
        muta.setBounds(0,0, 100, 100);


        label= new JLabel();
        label.setBounds(0, 0, larguraMax, comprimentoMax);

        //Dimensionando a janela
        janelas = new JLayeredPane();
        janelas.setPreferredSize(new Dimension(larguraMax, comprimentoMax));

        //Criando o label de Alien para aparecer na tela inicial
        urlNaveTitulo = Jogador.class.getResource("/Imagens/NaveAlien.gif");
        if(urlNaveTitulo!=null){
            ImageIcon gifNaveAlien = new ImageIcon(urlNaveTitulo);
            alienTitulo = new JLabel(gifNaveAlien);
            alienTitulo.setBounds(((larguraMax-getWidth()/2)/2)-200,(comprimentoMax-getWidth()/2)/2, 250, 250);
            alienTitulo.setName("alienTitulo");
            janelas.add(alienTitulo,JLayeredPane.PALETTE_LAYER);
        }

        //Criando o label do jogador para aparecer na tela inicial
        urlJogadorTitulo = Jogador.class.getResource("/Imagens/gifNave.gif");
        if(urlJogadorTitulo!=null){
            ImageIcon gifJogador = new ImageIcon(urlJogadorTitulo);
            playerTitulo = new JLabel(gifJogador);
            playerTitulo.setBounds(((larguraMax-getWidth()/2)/2)+800,((comprimentoMax-getWidth()/2)/2)-20, 250, 250);
            playerTitulo.setName("jogadorTitulo");
            janelas.add(playerTitulo,JLayeredPane.PALETTE_LAYER);
        }

        //Criando o label do tutorial
        tutorial= new JLabel("A  D  SPACEBAR");
        tutorial.setFont(new Font("Arial", Font.BOLD, 60));
        tutorial.setBounds(0,(int)(comprimentoMax/1.25),getWidth()/3,getHeight()/4);
        tutorial.setForeground(new Color(45, 182, 250, 128));
        tutorial.setOpaque(false);
        tutorial.setVisible(true);
        janelas.add(tutorial,JLayeredPane.PALETTE_LAYER);

        //Criando o gameOver
        gameOver=new JLabel("GAME OVER");
        gameOver.setFont(new Font("Arial",Font.BOLD,80));
        gameOver.setBounds((int)(larguraMax/2.8),(int)(comprimentoMax/8),getWidth()/2,getHeight()/4);
        gameOver.setForeground(new Color(255, 0, 0,255));
        gameOver.setOpaque(false);
        gameOver.setVisible(false);
        janelas.add(gameOver,JLayeredPane.PALETTE_LAYER);

         //Criando o highScore
        score="HIGH SCORE:"+scoreAnterior;
        highScore= new JTextArea(score);
        highScore.setFont(new Font("Arial", Font.BOLD, 40));
        highScore.setBounds((int)(larguraMax/2.6),(int)(comprimentoMax/1.5), 1000, 300);
        highScore.setForeground(new Color(255, 255, 255, 255));
        highScore.setOpaque(false);
        highScore.setEditable(false);
        highScore.setEnabled(false);
        janelas.add(highScore,JLayeredPane.DEFAULT_LAYER);

        //Criando o score atual
        novoScorePartida="SCORE:"+scoreAtual;
        scorePartida= new JTextArea(novoScorePartida);
        scorePartida.setFont(new Font("Arial", Font.BOLD, 16));
        scorePartida.setBounds((int)(larguraMax/1.1),0, 200, 200);
        scorePartida.setForeground(new Color(255, 255, 255, 255));
        scorePartida.setOpaque(false);
        scorePartida.setVisible(false);
        scorePartida.setEditable(false);
        scorePartida.setEnabled(false);
        janelas.add(scorePartida,JLayeredPane.DEFAULT_LAYER);

        //Captando a imagem de fundo e do titulo
        try{
            imagem= ImageIO.read(new File("Imagens/backgroundEstrelas.png"));
        }catch(IOException e){
            System.out.println("Arquivo de imagem nao econtrado");
        }
        try{
            titulo= ImageIO.read(new File("Imagens/Cosmic_Invasion.png"));
        }catch(IOException e){
            System.out.println("Arquivo de imagem nao econtrado");
        }

        //Colocando o background
        if(imagem!=null){
            Image imagemCarregada = imagem.getScaledInstance(getWidth(),getHeight(),Image.SCALE_SMOOTH);
            ImageIcon iconeDeImg = new ImageIcon(imagemCarregada);
            label.setIcon(iconeDeImg);
        }

        //Colocando o titulo
        if(titulo!=null){
            Image tituloRedi = titulo.getScaledInstance(getWidth()/2,getHeight()/2,Image.SCALE_SMOOTH);
            ImageIcon tituloDeImg = new ImageIcon(tituloRedi);
            title = new JLabel(tituloDeImg);
            title.setBounds((larguraMax-getWidth()/2)/2,(comprimentoMax-getWidth()/2)/2,getWidth()/2,getHeight()/2);
            janelas.add(title,JLayeredPane.PALETTE_LAYER);
        }


        //Criando sons e dando play no audio;
        backgroundMusic= new Sons("SoundTracks/SoundTrack.wav");
        gameOverSound= new Sons("SoundTracks/trilhaOver.wav");
        


        //Adicionando o Botao e o menu
        label.add(botao);
        label.add(menu);
        label.add(muta);
        janelas.add(label, JLayeredPane.DEFAULT_LAYER);
        setContentPane(janelas);

        //Encerrando ao fechar a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Redimensionando a tela
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redimensiona();
            }
        });

    }
    //Função para pegar a derrota e utilizar em outras funções
    public int getDerrota(){
        return derrota;
    }
   
    //Ações ao dar play clicando no botão jogar
    @Override
    public  void actionPerformed(ActionEvent eve){
        Object source = eve.getSource();
        if(source==botao){
            //Começa a musica
            if(derrota==1){
                gameOverSound.stop();
                if(som==1){
                    backgroundMusic.stop();
                    backgroundMusic.play();
                    backgroundMusic.loop();
                }
            }
            //Redefine derrota como não está derrotado
            derrota=0;
            //Some com o nave do titulo
            alienTitulo.setVisible(false);
            //Some com o jogador do titulo
            playerTitulo.setVisible(false);
            //Esconde o tutorial
            tutorial.setVisible(false);
            //Impede qu o botão de play seja visível e possa ser clicado
            botao.setVisible(false);
            botao.setEnabled(false);
            //Deixa o titulo e o highscore invisivel 
            title.setVisible(false);
            highScore.setVisible(false);
            //Permite a visão do score de partida
            scorePartida.setVisible(true);
            //Cria uma nova nave de jogador e adiciona a tela
            player1= new Jogador(larguraMax, comprimentoMax, janelas,listaAliens1, listaAliens1_2,listaAliens2, listaAliens2_2,listaAliens3, this,jogador);
            jogador.add(player1);
            yJogador= player1.getYNave();
            JLabel joga1 = player1.getNave();
            janelas.add(joga1, JLayeredPane.PALETTE_LAYER);
            
            //Recria a fileira de aliens
            recriaFileiras();

            //Começa a movimentação dos aliens da naveAlien roxa e dos Tiros dos Aliens
            moverAliens = new Timer(2000, e -> moveAliens());
            moverAliens.start();
            //Spawna a  primeira nave no inicio
            spawnaNaveAlien();
            naveCooldown= new Timer(60000,e ->spawnaNaveAlien());   
            naveCooldown.start();
            delayTiroAlien= new Timer(2000,e ->alienAtira());   
            delayTiroAlien.start();
            
            //Possibilitando a movimentação
            this.addKeyListener(player1);
            //Desabilita o gameOver e o menu
            gameOver.setVisible(false);
            menu.setVisible(false);
            menu.setEnabled(false);
        }
        if(source == menu){
            //Libera o titulo e volta para o inicio sem menu
            alienTitulo.setVisible(true);
            playerTitulo.setVisible(true);
            tutorial.setVisible(true);
            gameOverSound.stop();
            title.setVisible(true);
            gameOver.setVisible(false);
            menu.setVisible(false);
            menu.setEnabled(false);
            if(som==1){
                backgroundMusic.play();
                backgroundMusic.loop();
            }
        }
        if(source==muta){
            if(som==0){
                imagemSom= new ImageIcon("Imagens/sound.png");
                muta.setIcon(imagemSom);
                backgroundMusic.play();
                backgroundMusic.loop();
                som=1;
            }
            else{
                imagemSom= new ImageIcon("Imagens/mute.png");
                muta.setIcon(imagemSom);
                backgroundMusic.stop();
                som=0;
            }
        }
    
        //Vai permitir o foco na janela
        this.setFocusable(true);
        this.requestFocusInWindow(); 
    }

    //Função que permite a recriação das fileiras de Aliens
    public void recriaFileiras(){
        int numAliens;
        int x=200, y1=380, y2=310, y3=240, y4=170, y5=100;
        numAliens =larguraMax/150;
        //Irá criar objetos e labels aliens e adiciona-los ao Pane
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

            JLabel novoAlien1= alien1.getAlien();
            novoAlien1.putClientProperty("id",alien1.getId());

            JLabel novoAlien1_2= alien1_2.getAlien();
            novoAlien1_2.putClientProperty("id",alien1_2.getId());

            JLabel novoAlien2= alien2.getAlien();
            novoAlien2.putClientProperty("id",alien2.getId());

            JLabel novoAlien2_2= alien2_2.getAlien();
            novoAlien2_2.putClientProperty("id",alien2_2.getId());

            JLabel novoAlien3= alien3.getAlien();
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
    //Spawna a nave roxa alien
    public void spawnaNaveAlien(){
        NaveAlien naveAlien = new NaveAlien(larguraMax,janelas);
        JLabel novaNave = naveAlien.getNaveAlien();
        novaNave.putClientProperty("id",-1);
        janelas.add(novaNave, JLayeredPane.PALETTE_LAYER);
    }
    //Movimenta a fileira de aliens
    public void moveAliens(){
        int bateu=0;
        //Verifica se os aliens da primeira fileira atingiram a parede ou a distancia maxima do jogador
        for(Alien1 a1 : listaAliens1){
            a1.movimenta(direcao);
            if (a1.getX() <= 0 || a1.getX() >= larguraMax - 200) {
                bateu=1;
            }
            if(a1.getY()>=yJogador-120){
                derrota=1;
            }
        }
        //Verifica se os aliens da segunda fileira atingiram a parede ou a distancia maxima do jogador
        for(Alien1 a1_2 : listaAliens1_2){
            a1_2.movimenta(direcao);
            if (a1_2.getX() <= 0 || a1_2.getX() >= larguraMax - 120) {
                bateu=1;
            }
            if(a1_2.getY()>=yJogador-200){
                derrota=1;
            }
        }
        //Verifica se os aliens da terceira fileira atingiram a parede ou a distancia maxima do jogador
        for(Alien2 a2 : listaAliens2){
            a2.movimenta(direcao);
            if (a2.getX() <-120  || a2.getX() >= larguraMax - 120) {
                bateu=1;
            }
            if(a2.getY()>=yJogador-200){
                derrota=1;
            }
        }
        //Verifica se os aliens da quarta fileira atingiram a parede ou a distancia maxima do jogador
        for(Alien2 a2_2 : listaAliens2_2){
            a2_2.movimenta(direcao);
            if (a2_2.getX() <-120  || a2_2.getX() >= larguraMax - 120) {
                bateu=1;
            }
            if(a2_2.getY()>=yJogador-120){
                derrota=1;
            }
        }
        //Verifica se os aliens da quinta fileira atingiram a parede ou a distancia maxima do jogador
        for(Alien3 a3 : listaAliens3){
            a3.movimenta(direcao);
            if (a3.getX() <= 0 || a3.getX() >= larguraMax - 200) {
                bateu=1;
            }
            if(a3.getY()>=yJogador-120){
                derrota=1;
            }
        }
        


        //Caso algum alien tenha batido na parede faz com que todos vão para baixo
        if(bateu==1){
                Timer delay = new Timer(300, e -> {
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
        //Caso o jogador perca remove tudo menos os titulos e os scores
        if(derrota==1){
            for (Component comp : janelas.getComponents()){
                if (comp != label && comp !=title && comp!=highScore && comp!=scorePartida && comp!=gameOver && comp!= alienTitulo && comp!=playerTitulo && comp!=tutorial) {
                    janelas.remove(comp);
                }
            }
            //Define o jogador como derrotado e o remove
            player1.setDerrota(1);
            jogador.remove(player1);
            janelas.repaint();
            //Para o cooldown da nave roxa para o movimento dos aliens e os tiros dos aliens
            naveCooldown.stop();
            moverAliens.stop();
            delayTiroAlien.stop();
            //Limpa as listas de aliens
            listaAliens1.clear();
            listaAliens1_2.clear();
            listaAliens2.clear();
            listaAliens2_2.clear();
            listaAliens3.clear();
            //Libera o botao para jogar novamente e torna-o visível
            botao.setVisible(true);
            botao.setEnabled(true);
            //Libera o botão de menu e torna-o visível
            menu.setVisible(true);
            menu.setEnabled(true);
            //Torna gameOver visível
            gameOver.setVisible(true);
            //Define o novo HighScore caso seja maior
            mudaHighScore();
            //Permite a visão do highScore e impede a visão do score de partida
            highScore.setVisible(true);
            scorePartida.setVisible(false);
            //Parando musica de backGround e dando play na musica de gameOver
            if(som==1){
                backgroundMusic.stop();
            }
            gameOverSound.play();
            //Torna os identificadores ou ids dos aliens começando em 0 denovo
            identificadores=0;
        }
    }
    //Função para gerar o tiro dos aliens
    public void alienAtira(){
        JLabel tiroAlien;
        Random escolheLista = new Random();
        int lista = escolheLista.nextInt(5);
        Random alienRandom = new Random();
        int index;
        int posicaoX=300,posicaoY=300;
        Alien1 auxAlien1;
        Alien2 auxAlien2;
        Alien3 auxAlien3;
        int vazia=0;
        //Esse do while fará com que ele busque sempre que houver listas livres
        do{
            vazia=0;
            //Randomiza qual fileira de aliens será escolhida
            lista = escolheLista.nextInt(5);
            //Irá verificar se a lista escolhida está vazia caso não esteja escolhe um alien aleatório da lista para atirar
            if(lista==0){
                if(!listaAliens1.isEmpty()){
                    index = alienRandom.nextInt(listaAliens1.size());
                    auxAlien1=listaAliens1.get(index);
                    posicaoX=auxAlien1.getX();
                    posicaoY=auxAlien1.getY();
                }
                else{
                    vazia=1;
                }
            }
            if(lista==1){
                if(!listaAliens1_2.isEmpty()){
                    index = alienRandom.nextInt(listaAliens1_2.size());
                    auxAlien1=listaAliens1_2.get(index);
                    posicaoX=auxAlien1.getX();
                    posicaoY=auxAlien1.getY();
                }
                else{
                    vazia=1;
                }
            }
            if(lista==2){
                if(!listaAliens2.isEmpty()){
                    index = alienRandom.nextInt(listaAliens2.size());
                    auxAlien2=listaAliens2.get(index);
                    posicaoX=auxAlien2.getX();
                    posicaoY=auxAlien2.getY(); 
                }
                else{
                    vazia=1;
                }
            }
            if(lista==3){
                if(!listaAliens2_2.isEmpty()){
                    index = alienRandom.nextInt(listaAliens2_2.size());
                    auxAlien2=listaAliens2_2.get(index);
                    posicaoX=auxAlien2.getX();
                    posicaoY=auxAlien2.getY();
                }
                else{
                    vazia=1;
                }
            }
            if(lista==4){
                if(!listaAliens3.isEmpty()){
                    index = alienRandom.nextInt(listaAliens3.size());
                    auxAlien3=listaAliens3.get(index);
                    posicaoX=auxAlien3.getX();
                    posicaoY=auxAlien3.getY();
                }
                else{
                    vazia=1;
                }
            }
            //Caso a lista escolhida não esteja vazia irá realizar a criação e adição do tirp
            if(vazia==0){
                TiroAlien novoTiro= new TiroAlien(posicaoX,posicaoY, janelas,this,jogador,comprimentoMax);
                tiroAlien=novoTiro.getTiro();
                janelas.add(tiroAlien, JLayeredPane.PALETTE_LAYER); 
            }
            //Caso todas as listas estejam vazias irá parar o loop
            if(listaAliens1.isEmpty() && listaAliens1_2.isEmpty() && listaAliens2.isEmpty() && listaAliens2_2.isEmpty() && listaAliens3.isEmpty()){
                break;
            }
        }while(vazia!=0);
    }
    //Função para definir que o jogo foi perdido por outras funções
    public void setDerrota(){
        derrota=1;
    }

    //Função que altera o score da partida ao atingir um alien
    public void setScore(int num){
        scoreAtual+=num;
        scorePartida.setText("SCORE:"+scoreAtual);
    }
    //Função que irá mudar o highScore caso a pontuação da partida tenha sido maior que o último highScore
    public void mudaHighScore(){
        if(scoreAtual>=scoreAnterior){
            scoreAnterior=scoreAtual;
        }
        scoreAtual=0;
        setScore(scoreAtual);
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
        Jogo visualiza = new Jogo();
        visualiza.setVisible(true);
    }
}


    
