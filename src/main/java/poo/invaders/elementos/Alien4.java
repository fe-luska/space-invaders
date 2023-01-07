/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.invaders.elementos;

import javafx.scene.Group;
import static poo.invaders.Engine.getImage;

/**
 * Alien 4 é o alien vermelho que aparece aleatoriamente no topo da tela do jogo
 * @author Fernando Lucas
 */
public class Alien4 extends Alien {
    
    // Variavel que vai contar o tempo de CoolDown de respawn alien
    private double cooldown = 10;
    
    // Tempo minimo que o alien fica sem renascer após ser destruido
    private final double tempoCooldown = 10;
    
    /**
     * Constroi o Alien na posição e com velocidade dadas.
     * 
     * @param x - Posição horizontal
     * @param y - Posição vertical
     * @param vel - velocidade
     */
    public Alien4(int x, int y, int vel) {
        imagem1 = getImage("assets/img/alien4.png");
        this.getChildren().add(imagem1);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.velocidade = vel;
    }
    
    /**
     * Move horizontalmente, ao chegar no limite esquerdo da tela, inverte o 
     * sentido do movimento e se chegar ao limite direito, o alien morre.
     */
    public void moveX() {
        
        if (this.getX() < Math.abs(this.velocidade)) {
            this.setVelocidade(-getVelocidade());
        }
        
        if (this.getX() > 1080) {
            this.morreu();
        }
        
        this.setLayoutX(this.getX() + this.velocidade);
    }
    
    /**
     * Atualiza a logica do alien
     * 
     * @param root - raiz do jogo
     */
    public void update(Group root) {
        
        // Atualiza o tempo de cooldown
        if (this.cooldown != 0) {
            if (cooldown < 0.016)
                cooldown = 0;
            else
                cooldown -= 0.016;
        }
        
        if (this.isDead()) {
            // alien 4 está morto
            if (cooldown == 0 && Math.random() < 0.001) {
                // reviver alien4, chance de 0.1% por frame
                this.setDead(false);
                this.setLayoutX(1080);
                this.setVelocidade(-5);
                if (!root.getChildren().contains(this))
                    root.getChildren().add(this);
            }
        } else {
            // alien4 está vivo, atualizar movimento
            this.moveX();
        }
    }
    
    public void morreu() {
        this.setDead(true);
        this.cooldown = tempoCooldown;
    }
    
    @Override
    public int getNumber() {
        return 4;
    }
}
