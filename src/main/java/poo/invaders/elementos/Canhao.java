/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.invaders.elementos;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import static poo.invaders.Engine.getImage;

/**
 * Representa o jogador
 * @author Fernando Lucas
 */
public class Canhao extends Entidade {
    
    // Numero de vidas
    private int vidas = 3;
    
    // Velocidade do tiro
    private double velTiro = 2;
    
    // Imagem do canhao
    private final ImageView canhaoPNG = getImage("assets/img/ship.png");
    
    // Contador de tempo de cooldown
    private double cooldown = 0;
    
    // Cooldown entre tiros da nave
    private final double cooldownTiro = 0.2;
    
    // Flag de quando executar a animação de dano
    private boolean animacaoDano = false;
    
    // Tempo invencivel
    private double invencivel = 0;
    
    // Velocidade de movimento horizontal da nave
    private final double velHorizontal = 7;
            
    /**
     * Adiciona o canhao na posição inicial
     */
    public Canhao() {
        
        this.getChildren().add(canhaoPNG);
        this.setLayoutX(220);
        this.setLayoutY(650);
    }
    
    /**
     * Remove uma vida e volta o canhao para a posição inicial
     */
    public void levouTiro() {
        
        this.vidas--;
        
        if (this.vidas == 0) {
            this.setDead(true);
            return;
        }
        
        this.setLayoutX(220);
        this.setLayoutY(650);
        
        animacaoDano = true;
    }

    public double getVelTiro() {
        return velTiro;
    }

    public void setVelTiro(double velTiro) {
        this.velTiro = velTiro;
    }
    

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
    
    /**
     * Executa a anmiação de dano que dura 1 segundo, tempo que o canhao
     * fica invencivel.
     */
    private void animacaoDano() {
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(5);
        timeline.setAutoReverse(true);
        
        final KeyValue kv = new KeyValue(canhaoPNG.opacityProperty(), 0);
        final KeyFrame kf = new KeyFrame(Duration.millis(200), kv);
        timeline.getKeyFrames().add(kf);
        
        final KeyValue kv2 = new KeyValue(canhaoPNG.opacityProperty(), 1);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(200), kv2);
        timeline.getKeyFrames().add(kf2);
                
        timeline.setOnFinished((e) -> {    
            canhaoPNG.setOpacity(1);
        });
        
        timeline.play();
    }
    
    /**
     * Atualiza a logica do canhao, movendo-o ou atirando. Essa função
     * deve ser chamda a cada frame do jogo.
     * 
     * @param input - ArrayList que contém as teclas pressionadas
     * @param root - raiz do jogo
     * @param tiros - ArrayList que contem os tiros do jogo
     */
    public void update(ArrayList<String> input, Group root, ArrayList<Tiro> tiros) {
        
        if (this.getCooldown() != 0) {
            // canhão está em cooldown, atualizar tempo de cooldown
            if (this.getCooldown() < 0.016) {
                this.setCooldown(0);
            } else {
                this.setCooldown(this.getCooldown() - 0.016);
            }
        }
        
        if (this.getInvencivel() != 0) {
            // canhao está invencivel, atualizar contagem de tempo
            if (this.getInvencivel() < 0.016) {
                this.setInvencivel(0);
            } else {
                this.setInvencivel(this.getInvencivel() - 0.016);
            }
        }
        
        if (animacaoDano) {
            // Executa a animação de dano (invencivel)
            animacaoDano();
            animacaoDano = false;
        }
        
        if (input.contains("A")) {
            // mov para esquerda
            this.moveX(-velHorizontal);
        }
        
        if (input.contains("D")) {
            // mov para direita
            this.moveX(velHorizontal);
        }
        
//        if (input.contains("W")) {
//            this.moveY(-velHorizontal);
//        }
//        
//        if (input.contains("S")) {
//            this.moveY(velHorizontal);
//        }
        
        
        if (input.contains("SPACE") && this.getCooldown() == 0) {
            // atirar
            shoot(root, tiros);
            this.setCooldown(cooldownTiro);
        }
    }
    
    /**
     * Tiro do canhao. Cria um objeto Tiro com a velocidade de tiro e
     * coordenadas do canhao.
     * 
     * @param root - raiz do jogo
     * @param tiros - ArrayList que contém os tiros do jogo
     */
    public void shoot(Group root, ArrayList<Tiro> tiros) {
        
        Tiro tiro = new Tiro(this.getX() + 22, this.getY(), -this.getVelTiro());
        tiros.add(tiro);
        root.getChildren().add(tiro);

    }
    
    /**
     * Move horizontalmente a nave, porém apenas até os limites da tela
     * 
     * @param delta - px a serem movimentados
     */
    @Override
    public void moveX(double delta) {
        if (this.getX() < delta + 20 && delta < 0)
            // Limite esquerdo da tela
            return;
        else if (this.getX() > 1020 && delta > 0) {
            // Limite direito da tela
            return;
        }
        else
            // Executa o movimento
            this.setLayoutX(this.getX() + delta);
    }
    
    public double getCooldown() {
        return cooldown;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    public double getInvencivel() {
        return invencivel;
    }

    public void setInvencivel(double invencivel) {
        this.invencivel = invencivel;
    }
    
    @Override
    public boolean isDead() {
        if (this.getVidas() == 0)
            return true;
        else
            return false;
    }
    
}
