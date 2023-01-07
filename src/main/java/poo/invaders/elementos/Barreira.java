/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.invaders.elementos;

import java.util.ArrayList;
import javafx.geometry.BoundingBox;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import static poo.invaders.Engine.getImage;

/**
 * Barreiras do jogo. A barreira é uma matriz 6x3 em que cada retangulo é destruido
 * individualmente, possuindo assim uma destruição por area.
 * 
 * @author Fernando Lucas
 */
public class Barreira extends Entidade {
    
    // Estado é o numero de tiros que a barreira ainda pode levar
    private int estado = 18;
    
    // A matriz de retangulos é usada para calcular a area onde o tiro acertou
    private Rectangle[][] matrizRect = new Rectangle[6][3];
    
    // Matriz com as imagens da barreira
    private ImageView[][] matrizImg = new ImageView[6][3];
    
    /**
     * Constroi a barreira na posição XY dada
     * 
     * @param x - Posição horizontal
     * @param y - Posição vertical
     */
    public Barreira(int x, int y) {
        
        ImageView imagem = new ImageView();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                // Adiciona os retangulos que serão usados no calculo de colisão
                // da barreira
                matrizRect[j][i] = new Rectangle();
                matrizRect[j][i].setWidth(20);
                matrizRect[j][i].setHeight(24);
                matrizRect[j][i].setLayoutX(20*j);
                matrizRect[j][i].setLayoutY(24*i);
                matrizRect[j][i].setOpacity(0);
                this.getChildren().add(matrizRect[j][i]);
                
                // Adiciona as imagens à matriz da barreira
                imagem = getImage("assets/img/barrier/barreira" + i + j + ".png");
                matrizImg[j][i] = imagem;
                this.getChildren().add(matrizImg[j][i]);
            }
        }
        
        this.setLayoutX(x);
        this.setLayoutY(y);
    }
    
    /**
     * Checa colisões do tiro passado como argumento e a barreira
     * 
     * @param root - raiz do jogo
     * @param tiro - tiro a ser checado
     * @param tiros - ArrayList de tiros
     */
    public void checkColision(Group root, Tiro tiro, ArrayList<Tiro> tiros) {
        boolean colidiu = false;
        if (tiro.getBoundsInParent().intersects(this.getBoundsInParent())) {
            // Tiro está na area total da barreira, checar colisão com os retangulos
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 6; j++) {

                    // Calcula a posição do retangulo atual em relação às coordenadas
                    // do jogo para comparar com a posição do tiro.
                    BoundingBox areaRect = new BoundingBox(
                            this.getBoundsInParent().getMinX() + matrizRect[j][i].getBoundsInParent().getMinX(),
                            this.getBoundsInParent().getMinY() + matrizRect[j][i].getBoundsInParent().getMinY(), 
                            matrizRect[j][i].getWidth(), matrizRect[j][i].getHeight());
                    
                    
                    if (tiro.getBoundsInParent().intersects(areaRect) &&
                            matrizImg[j][i].getOpacity() != 0) {
                        // Tiro acertou o retangulo e ele não está marcado como destruido.
                        
                        matrizImg[j][i].setOpacity(0); // Marca como destruido
                        tiros.remove(tiro);
                        root.getChildren().remove(tiro);
                        this.estado--;
                        colidiu = true;
                        break;
                    }
                }
                if (colidiu)
                    break;
            }
        }
        
        if (this.estado == 0) {
            // Barreira totalmente destruida
            root.getChildren().remove(this);
        }
    }
}
