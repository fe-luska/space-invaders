/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.invaders.graphical;

import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import static poo.invaders.Engine.getImage;

/**
 * Animação de explosão. Criada no lugar de um alien após sua morte
 * @author Fernando Lucas
 */
public class Explosion extends Group {
    
    // Duração da explosão em segundos
    private final double duration = 0.25;
    
    // PNG da explosão
    private final ImageView explosionPNG = getImage("assets/img/explosion/explosionW2.png");
    
    /**
     * Cria a explosão na posição XY passada.
     * 
     * @param x - Posição horizontal
     * @param y - Posição vertical
     * @param root - Raiz do jogo
     */
    public Explosion(double x,double y, Group root) {
        
        explosionPNG.setLayoutX(x);
        explosionPNG.setLayoutY(y);
        this.getChildren().add(explosionPNG);
        
        // A explosão some suavemente com a animação de fade
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(duration), explosionPNG);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
        fadeOut.setOnFinished((e)-> {
            // Ao final da explosão, ela é removida do jogo
            root.getChildren().remove(this);
        });
    }
}
