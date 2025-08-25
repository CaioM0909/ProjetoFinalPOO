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
    private JButton botao;
    private JLabel label;
    private BufferedImage imagem;
    private JLayeredPane janelas;
    private Jogador player1;
    private Rectangle areaUtil;
    private int larguraMax;
    private int comprimentoMax;
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
    
    public int getDerrota(){
        return derrota;
    }
   

    @Override
    public  void actionPerformed(ActionEvent eve){
        derrota=0;
        botao.setVisible(false);
        botao.setEnabled(false);
        player1= new Jogador(larguraMax, comprimentoMax, janelas,listaAliens1, listaAliens1_2,listaAliens2, listaAliens2_2,listaAliens3, this);
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
                if (comp != label) {
                    janelas.remove(comp);
                }
            }
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
    private List<Alien1> lista1;
    private List<Alien2> lista2;
    private List<Alien3> lista3;
    private List<Alien1> lista4;
    private List<Alien2> lista5;
    private Teste janela;
        Jogador(int w, int h, JLayeredPane camadas, List<Alien1> listaAliens1,  List<Alien1> listaAliens1_2, List<Alien2> listaAliens2, List<Alien2> listaAliens2_2,List<Alien3> listaAliens3, Teste janelas){
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
        }
        public int getYNave(){
            return posicaoY;
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
            if(janela.getDerrota()!=1){
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
                        Tiro novoTiro= new Tiro(posicaoX,posicaoY, camada,lista1,lista4,lista2,lista5,lista3,janela);
                        tiro=novoTiro.getTiro();
                        camada.add(tiro, JLayeredPane.PALETTE_LAYER);
                        ultimoTiro = atual;    
                    }
                    
                    break;
                }
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
    private List<Alien1> lista1;
    private List<Alien1> lista1_2;
    private List<Alien2> lista2;
    private List<Alien2> lista2_2;
    private List<Alien3> lista3;
    private Teste janela;
    private static int vidasNave=3;
        Tiro(int x, int y, JLayeredPane camadas,List<Alien1> listaAliens1,List<Alien1> listaAliens1_2,List<Alien2> listaAliens2, List<Alien2> listaAliens2_2,List<Alien3> listaAliens3, Teste janelas){
            lista1=listaAliens1;
            lista1_2=listaAliens1_2;
            lista2= listaAliens2;
            lista2_2= listaAliens2_2;
            lista3= listaAliens3;
            janela=janelas;
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
                    Integer id = (Integer)((JComponent) comp).getClientProperty("id");

                    lista1.removeIf(a -> a.getId() == id);
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

class Alien1{
    private URL url;
    private JLabel novoAlien;
    private int xAtual,yAtual;
    private int id;
    Alien1(int x, int y){
        xAtual=x;
        yAtual=y;
        url = Alien1.class.getResource("/ProjetoFinal/Imagens/alien1.gif");
        ImageIcon icon = new ImageIcon(url);
        novoAlien = new JLabel(icon);
        novoAlien.setName("alien");
        novoAlien.setBounds(x, y, 250, 250);
    }

    public void setId(int identificador){
        id=identificador;
    }

    public int getId(){
        return id;
    }

    public void movimenta(int inverter){
        xAtual += 50 * inverter;
        novoAlien.setBounds(xAtual, yAtual, 250, 250);
    }
    public void descer(){
        yAtual += 50;
        novoAlien.setBounds(xAtual, yAtual, 250, 250);
    }

    public int getX(){
        return xAtual;
    }
    public int getY(){
        return yAtual;
    }

    public JLabel getAlien1(){
        return novoAlien;
    }
}
class Alien2{
    private URL url;
    private JLabel novoAlien;
    private int xAtual,yAtual;
    private int id;
    Alien2(int x, int y){
        xAtual=x;
        yAtual=y;
        url = Alien1.class.getResource("/ProjetoFinal/Imagens/alien2.gif");
        ImageIcon icon = new ImageIcon(url);
        novoAlien = new JLabel(icon);
        novoAlien.setName("alien");
        novoAlien.setBounds(x, y, 250, 250);
    }
    public void setId(int identificador){
        id=identificador;
    }

    public int getId(){
        return id;
    }


    public void movimenta(int inverter){
        xAtual += 50 * inverter;
        novoAlien.setBounds(xAtual, yAtual, 250, 250);
    }
    public void descer(){
        yAtual += 50;
        novoAlien.setBounds(xAtual, yAtual, 250, 250);
    }

    public int getX(){
        return xAtual;
    }
    public int getY(){
        return yAtual;
    }
    public JLabel getAlien2(){
        return novoAlien;
    }
}

class Alien3{
    private URL url;
    private JLabel novoAlien;
    private int xAtual,yAtual;
    private int id;
    Alien3(int x, int y){
        xAtual=x;
        yAtual=y;
        url = Alien1.class.getResource("/ProjetoFinal/Imagens/alien3.gif");
        ImageIcon icon = new ImageIcon(url);
        novoAlien = new JLabel(icon);
        novoAlien.setName("alien");
        novoAlien.setBounds(x, y, 250, 250);
    }
    public void setId(int identificador){
        id=identificador;
    }

    public int getId(){
        return id;
    }


    public void movimenta(int inverter){
        xAtual += 50 * inverter;
        novoAlien.setBounds(xAtual, yAtual, 250, 250);
    }
    public void descer(){
        yAtual += 50;
        novoAlien.setBounds(xAtual, yAtual, 250, 250);
    }

    public int getX(){
        return xAtual;
    }
    public int getY(){
        return yAtual;
    }
    public JLabel getAlien3(){
        return novoAlien;
    }
}

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
        url = Alien1.class.getResource("/ProjetoFinal/Imagens/NaveAlien.gif");
        ImageIcon icon = new ImageIcon(url);
        novaNave = new JLabel(icon);
        novaNave.setName("alien");
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