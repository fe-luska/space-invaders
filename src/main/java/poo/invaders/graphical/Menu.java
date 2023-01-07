/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.invaders.graphical;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import static poo.invaders.Engine.getImage;

/**
 * Menu inicial do jogo
 * 
 * @author Fernando Lucas
 */
public class Menu {
    
    // Largura e altura
    private static final int WIDTH = 1080;
    private static final int HEIGHT = 720;
    
    /**
     * Constroi o menu inicial do jogo
     * 
     * @param scene - scena do menu, não é a mesma do jogo
     * @param root - raiz do menu, não é a mesma do jogo
     */
    public Menu(Scene scene, Group root) {
        
        // Imagem de fundo do menu
        ImageView menuBack = getImage("assets/img/backgroundMenu.gif");
        menuBack.setFitWidth(WIDTH);
        menuBack.setFitHeight(HEIGHT);
        root.getChildren().add(menuBack);
        
        // Titulo do jogo
        ImageView titulo = getImage("assets/img/gameTitle2.gif");
        titulo.setLayoutX(250);
        titulo.setLayoutY(100);
        root.getChildren().add(titulo);
        
        // Texto inicial
        Text t = new Text("Pressione [espaço] para começar!");
        t.setX(420);
        t.setY(400);
        t.setScaleX(3);
        t.setScaleY(3);
        t.setStroke(Color.GREEN);
        root.getChildren().add(t);
    }
}
