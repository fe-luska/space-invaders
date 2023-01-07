/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.invaders.elementos;

import static poo.invaders.Engine.getImage;

/**
 * Alien 2 é o alien que fica no meio da matriz.
 * Concede 20 pontos ao jogador quando morto.
 * 
 * @author Fernando Lucas
 */
public class Alien2 extends Alien {
     
    /**
     * Constroi o Alien na posição e com velocidade dadas.
     * 
     * @param x - Posição horizontal
     * @param y - Posição vertical
     * @param vel - velocidade
     */
    public Alien2(int x, int y, double vel) {
        imagem1 = getImage("assets/img/alien2.1.png");
        imagem2 = getImage("assets/img/alien2.2.png");
        this.getChildren().add(imagem1);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.velocidade = vel;
    }
    
    @Override
    public int getNumber() {
        return 2;
    }
}
