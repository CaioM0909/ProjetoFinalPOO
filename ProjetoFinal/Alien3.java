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

class Alien3{
    private URL url;
    private JLabel novoAlien;
    private int xAtual,yAtual;
    private int id;
    Alien3(int x, int y){
        xAtual=x;
        yAtual=y;
        url = Alien1.class.getResource("Imagens/alien3.gif");
        ImageIcon icon = new ImageIcon(url);
        novoAlien = new JLabel(icon);
        novoAlien.setName("alien");
        novoAlien.putClientProperty("tipo", "alien3");
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
