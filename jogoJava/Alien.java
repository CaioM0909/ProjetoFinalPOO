package jogoJava;


import javax.swing.*;

import java.net.URL;

class Alien{
    private URL url;
    JLabel novoAlien;
    private int xAtual,yAtual;
    private int id;
    Alien(int x, int y, String caminho){
        xAtual=x;
        yAtual=y;
        url = Alien.class.getResource(caminho);
        if(url!=null){
            ImageIcon icon = new ImageIcon(url);
            novoAlien = new JLabel(icon);
        }
        if(url==null){
            System.out.println("Imagem alien não encontrada");
            novoAlien = new JLabel("Imagem Alien");
        }
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

    public JLabel getAlien(){
        return novoAlien;
    }
}

class Alien1 extends Alien{
    public Alien1(int x, int y){
        super(x, y, "/Imagens/alien1.gif");
        novoAlien.putClientProperty("tipo", "alien1");
    }
}
class Alien2 extends Alien{
    public Alien2(int x, int y){
        super(x, y, "/Imagens/alien2.gif");
        novoAlien.putClientProperty("tipo", "alien2");
    }
}
class Alien3 extends Alien{
    public Alien3(int x, int y){
        super(x, y, "/Imagens/alien3.gif");
        novoAlien.putClientProperty("tipo", "alien3");
    }
}
