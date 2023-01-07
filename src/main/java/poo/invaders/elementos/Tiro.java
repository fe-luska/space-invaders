/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.invaders.elementos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Representa um tiro no jogo
 * 
 * @author Fernando Lucas
 */
public class Tiro extends Entidade {
    
    // Velocidade do tiro, positiva para tiro descendo, negativa para tiro
    // que sobe
    private final double velocidade;
    
    // Retangulo que é o tiro
    private final Rectangle rect = new Rectangle();

    /**
     * Constroi o tiro na posição XY e com a velocidade dada
     * 
     * @param x - Posição horizontal do tiro
     * @param y - Posição vertical do tiro
     * @param vel - Velocidade do projetil
     */
    public Tiro(int x, int y, double vel) {
        rect.setWidth(5);
        rect.setHeight(20);
        rect.setFill(Color.RED);
        this.getChildren().add(rect);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.velocidade = vel;
    }
    
    public void moveY(double delta) {
        this.setLayoutY(this.getY() + delta);
    }
    
    public double getVel() {
        return velocidade;
    }
    
    /**
     * Atualiza o movimento do tiro.
     */
    public void update() {
        this.moveY(10*this.getVel());
    }
}
