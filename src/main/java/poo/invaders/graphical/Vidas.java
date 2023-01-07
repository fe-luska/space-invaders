/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo.invaders.graphical;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static poo.invaders.Engine.getImage;
import poo.invaders.elementos.Canhao;

/**
 * Mostra graficamente quantas vidas o jogador possui na parte superior esquerda
 * da tela através de corações cheios e corações vazios.
 * 
 * @author Fernando Lucas
 */
public class Vidas {
    
    // ArrayList que armazena os PNG das vidas
    private final ArrayList<ImageView> vidas = new ArrayList<>();
    
    /**
     * Cria a representação grafica das vidas
     * 
     * @param root - raiz do jogo
     * @param nVidas - numero inicial de vidas
     */
    public Vidas(Group root, int nVidas) {
        
        // Texto "Vidas:"
        Text vidasTXT = new Text("Vidas:");
        vidasTXT.setX(50);
        vidasTXT.setY(45);
        vidasTXT.setScaleX(2);
        vidasTXT.setScaleY(2);
        vidasTXT.setFill(Color.RED);
        root.getChildren().add(vidasTXT);
        
        // Carrega as vidas iniciais
        for (int i = 0; i < nVidas; i++) {
            ImageView vidaCheiaPNG = getImage("assets/img/coracaoCheio.png");
            vidaCheiaPNG.setLayoutX(125 + 60*i);
            vidaCheiaPNG.setLayoutY(20);
            root.getChildren().add(vidaCheiaPNG);
            vidas.add(vidaCheiaPNG);
        }
    }
    
    /**
     * Atualiza o numero de vidas exibidas
     * 
     * @param root - raiz do jogo
     * @param canhao - jogador
     * @param n - numero de vidas a serem ganhadas os perdidas
     */
    public void update(Group root, Canhao canhao, int n) {
        if (n < 0) {
            // perdeu vida
            for (int i = 0; i < -n; i++) {
                
                // Remove a imagem de vida cheia
                ImageView vida = vidas.get(canhao.getVidas());
                vidas.remove(vida);
                root.getChildren().remove(vida);
                
                // Coloca a imagem de vida vazia no lugar
                ImageView vidaVaziaPNG = getImage("assets/img/coracaoVazio.png");
                vidaVaziaPNG.setLayoutX(vida.getLayoutX());
                vidaVaziaPNG.setLayoutY(vida.getLayoutY());
                root.getChildren().add(vidaVaziaPNG);
                vidas.add(canhao.getVidas(),vida);
            }
        } else if (n > 0) {
            // ganhou vida
            if (canhao.getVidas() < 3) {
                for (int i = 0; i < n; i++) {
                    
                    // Remove a imagem de vida vazia
                    ImageView vida = vidas.get(canhao.getVidas() + i);
                    vidas.remove(vida);
                    root.getChildren().remove(vida);
                    
                    // Coloca a imagem de vida cheia no lugar
                    ImageView vidaVaziaPNG = getImage("assets/img/coracaoCheio.png");
                    vidaVaziaPNG.setLayoutX(vida.getLayoutX());
                    vidaVaziaPNG.setLayoutY(vida.getLayoutY());
                    vidas.add(canhao.getVidas() + i, vida);
                    root.getChildren().add(vidaVaziaPNG);
                }
            }
        }
    }
}
