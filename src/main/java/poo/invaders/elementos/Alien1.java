/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.invaders.elementos;
import static poo.invaders.Engine.getImage;

/**
 * Alien 1 é o alien que fica mais acima da matriz.
 * Concede 30 pontos ao jogador quando morto.
 * 
 * @author Fernando Lucas
 */
public class Alien1 extends Alien {
    
    /**
     * Constroi o Alien na posição e com velocidade dadas.
     * 
     * @param x - Posição horizontal
     * @param y - Posição vertical
     * @param vel - velocidade
     */
    public Alien1(int x, int y, double vel) {
        imagem1 = getImage("assets/img/alien1.1.png");
        imagem2 = getImage("assets/img/alien1.2.png");
        this.getChildren().add(imagem1);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.velocidade = vel;
    } 
    
    @Override
    public int getNumber() {
        return 1;
    }
}
