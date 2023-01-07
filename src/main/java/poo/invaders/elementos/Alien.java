/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.invaders.elementos;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

/**
 * Classe abstrata alien que será mãe das classes alien1 a alien4.
 * @author Fernando Lucas
 */
public abstract class Alien extends Entidade {
    
    // Velocidade de movimento dos aliens, velocidade positiva indica mov para
    // direita e negativa mov para esquerda
    protected double velocidade;
    
    // Velocidade dos tiros disparados pelos aliens
    protected double velDisparo = 1;
    
    //PNG dos aliens
    protected ImageView imagem1;
    protected ImageView imagem2;
    
    // Variavel que guarda quanto os aliens ja se movimentaram na direção horizontal
    // utilizada para saber quando descer uma linha e inverter o movimento
    private double movTotal = 0;
    
    // Guarda qual das imagens do alien está sendo exibida
    protected int imagemAtual = 1;
    
    /**
     * Atualiza a imagem do alien.
     */
    public void attImagem() {
        if (imagemAtual == 1) {
            // muda pra imagem 2
            this.getChildren().remove(imagem1);
            this.getChildren().add(imagem2);
            imagemAtual = 2;
        } else {
            // muda pra imagem 1
            this.getChildren().remove(imagem2);
            this.getChildren().add(imagem1);
            imagemAtual = 1;
        }
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }
    
    /**
     * Atualiza o mov do alien. Caso ele esteja em um dos limites horizontais
     * o alien descerá uma linha e inverterá o sentido do movimento. Há 20% de
     * chance do alien atualizar sua imagem ao executar essa função.
     * 
     * @param root - Raiz do jogo
     * @param entidades - ArrayList que contém os aliens do jogo
     */
    public void updateMov(Group root, ArrayList<Entidade> entidades) {
        
        if (this.isDead()) {
            // Alien marcado como morto, remover alien
            root.getChildren().remove(this);
            entidades.remove(this);
            return;
        }
        
        if (movTotal >= (400 - Math.abs(this.getVelocidade()))) {
            // alien está em um dos limites laterais,
            // descer uma linha e trocar sentido do mov
            
            if (this.getVelocidade() > 0) {
                this.moveX(400 - movTotal);
            } else {
                this.moveX(movTotal - 400);
            }
            
            this.moveY(20.0);
            movTotal = 0;
            
            // Inverte o sentido do movimento
            this.setVelocidade(-this.getVelocidade());
        } else {
            // movimento horizontal
            this.moveX(this.getVelocidade());
            movTotal += Math.abs(this.getVelocidade());
        }
        
        if (Math.random() > 0.8)
            // chance de atualizar a imagem de 20%
            this.attImagem();
    }
    
    /**
     * Faz o alien atirar para baixo de acordo com a chance de atirar
     * passada.
     * 
     * @param root - raiz do jogo
     * @param tiros - ArrayList que contém os tiros
     * @param chance - chance do alien atirar, double de 0 a 1.
     */
    public void shoot(Group root, ArrayList<Tiro> tiros, double chance) {
        if (Math.random() <= chance) {
            Tiro tiro = new Tiro(this.getX() + 22, this.getY(), this.velDisparo);
            tiros.add(tiro);
            root.getChildren().add(tiro);
        }
    }
    
    // Retorna o numero do alien
    public abstract int getNumber();

    public double getMovTotal() {
        return movTotal;
    }
}
