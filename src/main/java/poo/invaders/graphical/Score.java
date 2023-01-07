/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.invaders.graphical;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Contador de pontuação que fica no topo direito da tela
 * 
 * @author Fernando Lucas
 */
public class Score {
    
    // Texto da pontuação
    private final Text txtPontos;
    
    // Pontuação
    private int score = 0;
    
    /**
     * Construtor do contador de pontos
     * 
     * @param root - raíz do jogo
     */
    public Score(Group root) {
        txtPontos = new Text("Pontuação: < 00000 >");
        txtPontos.setX(700);
        txtPontos.setY(45);
        txtPontos.setScaleX(2);
        txtPontos.setScaleY(2);
        txtPontos.setStroke(Color.WHITE);
        root.getChildren().add(txtPontos);
    }
    
    /**
     * Atualiza o contador de pontos.
     * 
     * @param pontos - Pontos a serem adicionados ou subtraidos da pontuação
     * total
     */
    public void update(int pontos) {
        score += pontos;
        txtPontos.setText("Pontuação: < ");
        
        double atual = score;
        while (atual < 10000) {
            // Adiciona 0's a esquerda dos pontos, para a pontuação sempre
            // ter a mesma quantidade de digitos
            txtPontos.setText(txtPontos.getText() + "0");
            atual = atual * 10;
        }
        
        txtPontos.setText(txtPontos.getText() + score + " >");
    }
    
    /**
     * Volta a pontuação a 0
     */
    public void reset() {
        score = 0;
        txtPontos.setText("Pontuação: < 00000 >");
    }

    public int getScore() {
        return score;
    }
    
}
