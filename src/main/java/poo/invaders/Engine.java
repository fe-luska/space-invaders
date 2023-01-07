package poo.invaders;

import poo.invaders.graphical.*;
import poo.invaders.elementos.*;
import static javafx.scene.input.KeyCode.SPACE;
import java.util.ArrayList;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.Group;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * ENGINE DO JOGO
 * 
 * @author Fernando Lucas
 */
public class Engine extends Application {
    
    // raiz do jogo, do menu e da tela de gameOver
    private final Group root = new Group();
    private final Group menuRoot = new Group();
    private final Group gameOverScreen = new Group();
    
    // jogador
    private final Canhao canhao = new Canhao();
    
    // ArrayLists que armazenarão as entidades, barreiras e tiros do jogo
    private final ArrayList<Entidade> entidades = new ArrayList();
    private final ArrayList<Barreira> barreiras = new ArrayList();
    private final ArrayList<Tiro> tiros = new ArrayList();
    
    // Contador de tempo do nivel e de coolDown da atualização de lógica dos aliens
    private double tempoNivel = 0, coolDownAliens = 0;
    
    // ArrayList onde é salvo as teclas que estão sendo pressionadas
    private final ArrayList<String> input = new ArrayList<>();
    
    // Canvas e cena do jogo
    private final Scene level = new Scene(root);
    private Canvas canvas;
    
    // flags de pausa e game over
    private boolean pausado = true;
    private boolean gameOver = false;
    
    // alien4
    private final Alien4 alien4 = new Alien4(1000, 60, -5);
    
    // animationTimer
    private AnimationTimer timer = null;
    
    // Contador de nivel
    private int lvl = 0;
    
    // Contador gráfico de vidas e pontos
    private Vidas vidas;
    private Score score;
    
    // Texto exibido quando o jogo é pausado
    private Text txtPausa;
    
    /**
     * Método que é chamado pela função launch(). Aqui começa o programa.
     * 
     * @param stage - stage unico do jogo
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        stage.setTitle("Space Invaders");
        
        // Cria o menu inicial
        Scene menuInicial = new Scene(menuRoot);
        Menu menu = new Menu(menuInicial, menuRoot);
        stage.setScene(menuInicial);
        stage.setResizable(false);
        stage.show();
        
        // Inicia o jogo (o jogo é carregado antes mesmo do espaço ser apertado)
        iniciarJogo();
        
        // Aperte espaço para iniciar o jogo
        menuInicial.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                stage.setScene(level);
                pausado = false;
            }
        });
    }
    
    /**
     * Inicia todos os elementos do jogo. Deve ser chamado uma unica vez
     * antes do inicio do jogo.
     * 
     * @throws Exception 
     */
    private void iniciarJogo() throws Exception {
        
        // Canvas
        canvas = new Canvas(1080,720);
        root.getChildren().add(canvas);
        
        // Imagem de fundo
        ImageView background = getImage("assets/img/background1.jpg");
        background.setFitHeight(720);
        background.setFitWidth(1080);
        root.getChildren().add(background);
        
        // Adiciona o canhao ao root
        root.getChildren().add(canhao);
        
        // Inicia elementos básicos do jogo
        vidas = new Vidas(root, canhao.getVidas());
        iniciarKeyListener(level);
        iniciarTimer();
        iniciarEntidades(8, lvl);
        score = new Score(root);
        timer.start();
    }
    
    /**
     * Inicia o timer unico do jogo e sobrescreve o método handle() que é
     * executado uma vez por frame do jogo.
     */
    private void iniciarTimer() {
        timer = new AnimationTimer() {
            
            /**
             * Atualiza a lógica do jogo uma vez por frame (60 vezes por
             * segundo) através da função update(). Verifica se o jogo está
             * pausado, perdido, ou se é necessário passar de nível.
             */
            @Override
            public void handle(long now) {
                // metodo chamado a cada frame

                if (entidades.isEmpty()) {
                    // passou de nivel
                    levelUp();
                    pausarJogo();
                }
                
                if (!pausado && !gameOver) {
                    // jogo nao pausado e não game over    
                    
                    if (canhao.isDead()) {
                        // Testa se o canhao está vivo
                        gameOver();
                        pausado = true;
                    }
                    
                    update(); // atualiza a logica normalmente
                    
                    // Pressione ESC para pausar
                    level.setOnKeyPressed(e -> {
                        switch (e.getCode()) {
                            case ESCAPE:
                                pausarJogo();
                        }
                    });
                } else if (gameOver == false) {
                    // jogo pausado
                    
                    // Pressione ESPAÇO para despausar
                    level.setOnKeyPressed(e -> {
                        switch (e.getCode()) {
                            case SPACE:
                                pausado = false;
                                root.getChildren().remove(txtPausa);
                        }
                    });
                } else {
                    // game over
                    
                    // Pressione ESPAÇO para tentar de novo
                    level.setOnKeyPressed((KeyEvent e) -> {
                        switch (e.getCode()) {
                            case SPACE:
                                // Limpa e reseta todos os elementos do jogo
                                cleanLevel();
                                root.getChildren().remove(gameOverScreen);
                                score.reset();
                                root.setEffect(null);
                                gameOver = false;
                                lvl = -1;
                                vidas.update(root, canhao, 3);
                                canhao.setVidas(3);
                                try {
                                    levelUp();
                                } catch (Exception b) {
                                    System.out.println("Erro: " + b.getMessage());
                                }
                        }
                    });
                }
            }
        };
    }
    
    /**
     * Pausa o jogo e imprime a mensagem de pausa
     */
    private void pausarJogo() {
        pausado = true;
        txtPausa = new Text("                Jogo pausado!\n"
                          + "Pressione ESPAÇO para despausar!");
        txtPausa.setFill(Color.YELLOW);
        txtPausa.setX(435);
        txtPausa.setY(350);
        txtPausa.setScaleX(3);
        txtPausa.setScaleY(3);
        root.getChildren().add(txtPausa);
    }
    
    /**
     * inicia o KeyListener do jogo. Deve ser chamado uma vez apenas.
     * 
     * @param scene - cena atual.
     */
    private void iniciarKeyListener(Scene scene) {
        // As teclas que estão sendo pressionadas serão adicionadas em um array
        // "input" e removidas do array quando as teclas sao soltas. Essa abordagem
        // permite o jogador pressionar multiplas teclas ao mesmo tempo.

        // Pressionou uma tecla
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                String code = key.getCode().toString();
                
                if (!input.contains(code))
                        input.add(code);
        });
        
        // Soltou uma tecla
        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
                String code = key.getCode().toString();
                
                input.remove(code);
        });
    }
    
    /**
     * Imprime a tela de Game Over.
     */
    private void gameOver() {

        gameOver = true;
        canhao.setDead(true);
        
        // Efeito preto e branco:
        ColorAdjust filtro = new ColorAdjust();
        filtro.setSaturation(-1);
        root.setEffect(filtro);
        
        // PNG do game over
        ImageView gameOverText = getImage("assets/img/gameOver.png");
        gameOverText.setFitHeight(300);
        gameOverText.setFitWidth(600);
        gameOverText.setLayoutX(250);
        gameOverText.setLayoutY(170);
        gameOverScreen.getChildren().add(gameOverText);
        
        // Retangulo para tornar o texto mais visivel
        Rectangle rect = new Rectangle(695,90);
        rect.setLayoutX(210);
        rect.setLayoutY(510);
        gameOverScreen.getChildren().add(rect);
        
        // Texto de game over
        Text pressione = new Text("Pressione espaço para recomeçar\n"
                                + "         Sua pontuação: " + score.getScore());
        pressione.setFill(Color.WHITE);
        pressione.setX(450);
        pressione.setY(550);
        pressione.setScaleX(3);
        pressione.setScaleY(3);
        gameOverScreen.getChildren().add(pressione);
        root.getChildren().add(gameOverScreen);
    }
    
    /**
     * Passa de nivel. É chamada quando não há mais aliens vivos, limpa
     * os tiros e as barreiras restantes, da mais uma vida ao jogador (caso nao
     * exceda o limite de 3 vidas) e cria novamente os aliens para o proximo nivel.
     */
    public void levelUp() {
        // jogador passou de nivel
        // limpar nivel e começar proximo nivel

        cleanLevel();
        if (canhao.getVidas() < 3) {
            // da mais uma vida ao jogador
            vidas.update(root, canhao, 1);
            canhao.setVidas(canhao.getVidas() + 1);
        }
        
        lvl++;
        tempoNivel = 0;
        canhao.setLayoutX(220);
        
        try {
            // Inicia novamente os aliens, porém mais rapidos e uma linha (20px)
            // abaixo do nivel anterior.
            iniciarEntidades(8 + lvl/2, lvl);
            timer.start();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        
    }
    
    /**
     * Limpa os elementos gráficos do nivel. Deve ser chamada antes de
     * se passar de nivel.
     */
    public void cleanLevel() {
        
        while (!entidades.isEmpty()) {
            Entidade entidade = entidades.get(0);
            entidades.remove(entidade);
            root.getChildren().remove(entidade);
        }
        
        while (!barreiras.isEmpty()) {
            Barreira barreira = barreiras.get(0);
            barreiras.remove(barreira);
            root.getChildren().remove(barreira);
        }
        
        if (!alien4.isDead()) {
            alien4.morreu();
            root.getChildren().remove(alien4);
        }
        
        while (!tiros.isEmpty()) {
            Tiro tiro = tiros.get(0);
            tiros.remove(tiro);
            root.getChildren().remove(tiro);
        }
        
    }
    
    /**
     * Inicia as barreiras e os aliens do tipo 1, 2 e 3. Aliens são salvos
     * na ArrayList entidades e barreiras na ArrayList barreiras.
     * 
     * @param velAliens - Quantos px serão andados horizontalmente pelos aliens
     * a cada atualização de lógica. Aumenta de acordo com o nível.
     * @param nLinhas - Numero de linhas que os aliens começarão abaixo. Aumenta
     * de acordo com o nivel.
     */
    public void iniciarEntidades(double velAliens, int nLinhas) {

        int x; int y = 0;
        
        // reseta o cooldown para o alien4 nascer
        alien4.morreu();
        
        // Cria 4 barreiras:
        for (int i = 0; i < 4; i ++) {
            Barreira barreira = new Barreira(120 + 240*i, 530);
            barreiras.add(barreira);
            root.getChildren().add(barreira);
        }
        
        // cria os aliens do tipo 1:
        for (x = 0; x < 11; x++) {
            
            Alien1 alien = new Alien1(22 + 60 * x, 80 + (y * 60) + 20 * nLinhas, velAliens);
            
            entidades.add(alien);
            root.getChildren().add(alien);
        }
        
        // cria os aliens do tipo 2:
        for (y = 1; y < 3; y++) { 
            for (x = 0; x < 11; x++) {
                Alien alien = new Alien2(20 + 60 * x,80 + (y * 47) + 20 * nLinhas, velAliens);
                
                root.getChildren().add(alien);
                entidades.add(alien);
            }
        }
        
        // cria os aliens do tipo 3:
        for (y = 3; y < 5; y++) {
            for (x = 0; x < 11; x++) {
                Alien alien = new Alien3(20 + 60 * x,80 + (y * 45) + 20 * nLinhas, velAliens);
                
                root.getChildren().add(alien);
                entidades.add(alien);
            }
        }
    }

    /**
     * Atualiza a lógica do jogo. Chama outras funções para atualizar a
     * lógica de cada elemento do jogo. Método chamado a cada frame do jogo,
     * a não ser que esteja pausado ou que seja um game over.
     */
    private void update() {
        coolDownAliens += 0.016;
        tempoNivel += 0.016;
        
        // Atualiza a logica dos invasores:
        if (entidades.size() == 1) {
            // Rotina personalizada quando só sobra um alien
            updateAliens();
        } else if (!entidades.isEmpty()) {
            // Velocidade de atualização dos aliens aumenta conforme vão sendo
            // destruídos e conforme a progressão de nivel.
            double attAliens = (0.25 - lvl/15) * (Math.exp(0.02 * entidades.size()) - 0.9);
            if (coolDownAliens > attAliens) {
                updateAliens();
                coolDownAliens = 0;
            }
        }
        
        // Atualiza a logica dos seguintes elementos
        updateTiros();
        canhao.update(input, root, tiros);
        alien4.update(root);
        
        // Checagem de colisões
        checkColisions();
    }
    
    /**
     * Checa as colisões de tiros com aliens, barreiras e com o jogador.
     */
    private void checkColisions() {
        for (int i = 0; i < tiros.size(); i ++) {
            try {
                Tiro tiro = tiros.get(i);
                
                if (tiro.getY() < -5 || tiro.getY() > 730) {
                    // tiro fora dos limites, remover tiro
                    tiros.remove(tiro);
                    root.getChildren().remove(tiro);
                    continue;
                }
                
                if (tiro.getVel() < 0) {
                    // Tiro está subindo, ou seja, foi disparado pelo jogador
                    // Verificar colisões com aliens
                    for (int j = 0; j < entidades.size(); j++) {
                        // Itera por todos os aliens vivos
                        Alien alien = (Alien) entidades.get(j);
                        
                        if (tiro.getBoundsInParent().intersects(alien.getBoundsInParent())) {
                            // tiro atinjiu alien
                            
                            // Remove o alien
                            alien.setDead(true);
                            root.getChildren().remove(alien);
                            entidades.remove(alien);
                            
                            // Atualiza os pontos de acordo com o numero do alien
                            score.update(10 * Math.abs(alien.getNumber() - 4));
                            
                            // Adiciona efeito de explosão.
                            // Obs: a explosão é removida do root automaticamente
                            // ao final.
                            Explosion explosao = new Explosion(alien.getX(), alien.getY(), root);
                            root.getChildren().add(explosao);
                            
                            // remove o tiro
                            root.getChildren().remove(tiro);
                            tiros.remove(tiro);
                            break;
                        }
                    }

                    if (!alien4.isDead() && tiro.getBoundsInParent().intersects(alien4.getBoundsInParent())) {
                        // caso o tiro atinja o alien4
                        
                        // Atualiza a pontuaçõa
                        score.update(50);
                        
                        // Remove alien 4
                        alien4.morreu();
                        root.getChildren().remove(alien4);
                        
                        // Adiciona a explosão
                        Explosion explosao = new Explosion(alien4.getX(), alien4.getY(), root);
                        root.getChildren().add(explosao);
                        
                        // Remove o tiro
                        tiros.remove(tiro);
                        root.getChildren().remove(tiro);
                    }
                } else {
                    // tiro disparado pelos aliens, checar colisão com o jogador
                    if (tiro.getBoundsInParent().intersects(canhao.getBoundsInParent()) &&
                            canhao.getInvencivel() == 0) {
                        // tiro alienigena colidiu com canhao e canhao não está invencivel
                        
                        // Remove uma vida do jogador
                        canhao.levouTiro();
                        
                        // Atualiza o mostrador de vidas
                        vidas.update(root, canhao, -1);
                        
                        // Pausa o jogo
                        if (canhao.getVidas() != 0)
                            pausarJogo();
                        
                        // Canhao fica invencivel por 1 segundo
                        canhao.setInvencivel(1);
                        
                        // Remove o tiro
                        tiros.remove(tiro);
                        root.getChildren().remove(tiro);
                        break;
                    }
                }
                
                for (int j = 0; j < barreiras.size(); j++) {
                    // checa colisoes com as barreiras
                    Barreira barreira = (Barreira) barreiras.get(j);
                    barreira.checkColision(root, tiro, tiros);
                }
                
            } catch (IndexOutOfBoundsException e) {
                // Erro que já foi corrigido.
                System.out.println("Erro colisão: " + e.getMessage());
                break;
            }
        }
        
        for (int j = 0; j < entidades.size(); j++) {
            Alien alien = (Alien) entidades.get(j);
            // Checa se os aliens estão no limite vertical
            if (alien.getY() > 610) {
                gameOver();
            }
        }
    }
    
    /**
     * @brief Chama o método update para todos os tiros, onde cada tiro
     * terá sua lógica atualizada
     */
    public void updateTiros() {
        for (int i = 0; i < tiros.size(); i++) {
            Tiro tiro = tiros.get(i);
            tiro.update();
        }
    }
    
    /**
     * Atualiza o estado dos aliens, executando suas ações basicas.
     */
    private void updateAliens() {
        
        if (entidades.size() == 1) {
            // rotina personalizada quando só sobra 1 alien
            
            // Atualiza movimento e há uma maior chance de atirar.
            Alien alien = (Alien) entidades.get(0);
            alien.updateMov(root, entidades);
            alien.shoot(root, tiros, 0.01*Math.pow(1.020, tempoNivel));
            
        } else {
            for (int i = 0; i < entidades.size(); i++) {
                // Atualiza movimento e há uma chance de atirar.
                Alien alien = (Alien) entidades.get(i);
                alien.updateMov(root, entidades);
                alien.shoot(root, tiros, 0.01*Math.pow(1.0125, tempoNivel));
            }
        }
    }
   
    public static void main(String [] args) {
        launch(args);
    }
    
    /**
     * Carrega uma imagem com um dado caminho em um ImageView.
     * 
     * @param path - caminho da imagem
     * @return ImageView com a imagem, ou null caso a imagem nao seja encontrada
     */
    public static ImageView getImage(String path) {
        try {
            FileInputStream input = new FileInputStream(path);
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            return imageView;
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        }
    }
}