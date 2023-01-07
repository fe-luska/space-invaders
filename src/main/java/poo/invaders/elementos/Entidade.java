/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.invaders.elementos;

import javafx.scene.Group;


/**
 * Representa uma entidade no jogo. Classe mãe de aliens, barreiras, canhao
 * e tiros.
 * 
 * @author Fernando Lucas
 */
public abstract class Entidade extends Group {
    
    protected boolean dead = false;
    
    /**
     * @return Posição X da entidade
     */
    public int getX() {
        return (int) this.getLayoutX();
    }
    
    /**
     * @return Posição Y da entidade
     */
    public int getY() {
        return (int) this.getLayoutY();
    }
    
    /**
     * Move a entidade horizontalmente
     * 
     * @param delta - px a serem movidos.
     */
    public void moveX(double delta) {
        this.setLayoutX(this.getX() + delta);
    }
    
    /**
     * Move a entidade verticalmente
     * 
     * @param delta - px a serem movidos
     */
    public void moveY(double delta) {
        this.setLayoutY(this.getY() + delta);
    }

    /**
     * Checa se a entidade está morta
     * @return TRUE se a entidade está morta, FALSE caso contrario
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Define se a entidade está morta
     * @param dead 
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
