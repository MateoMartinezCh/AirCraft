/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.stages;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import pedro.ieslaencanta.com.dawairtemplate.model.Coordenada;
import pedro.ieslaencanta.com.dawairtemplate.model.Level;
import pedro.ieslaencanta.com.dawairtemplate.model.Player;
import pedro.ieslaencanta.com.dawairtemplate.model.Rectangle;
import pedro.ieslaencanta.com.dawairtemplate.model.Size;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.FactoryBullets;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.FactoryEnemies;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.Fighter;

/**
 *
 * @author Pedro
 */
public class GameStage extends AbstractScene {

    private ArrayList<Level> niveles;
    private Level actual;
    private int indicenivel;
    private Size s;
    private static Font font;//, Font.ITALIC, 50);
    private Player player;
    private Image imgvida;
    private Fighter fighter;
    private MediaPlayer sonidomegolpean;
    private String pathsonido = "explosion.mp3";
    private String pathurl = "avion.png";
    private static float[][] probabilidades = {
        {0.2f, 1f},
        {0.6f, 1f},
        {0.5f, 1f}
    };

    static {
        font = new Font("8BIT WONDER Nominal", 30);//, Font.ITALIC, 50);
    }

    //0 delante, 1 detras,2 arriba, 3 abajo, 4 disparo
    //private boolean[] keys_presed;
    public GameStage(GraphicsContext context, GraphicsContext bg_context, Size s) {
        super(context, bg_context, s, null);
        this.indicenivel = 0;
        this.niveles = new ArrayList<>();
        this.s = s;
        this.state = SceneState.PRE_STARTED;
        this.player = new Player();
        this.crearNiveles(bg_context);
        this.actual = this.niveles.get(indicenivel);
        this.initFactory();
        this.imgvida = new Image(getClass().getResourceAsStream("/" + this.pathurl));
        ClassLoader classLoader = getClass().getClassLoader();
        this.sonidomegolpean = new MediaPlayer(new Media(classLoader.getResource(this.pathsonido).toString()));
        this.fighter = new Fighter(
                5,
                new Size(74, 26),
                new Coordenada(20, s.getHeight() / 2),
                new Rectangle(new Coordenada(0, 0), new Coordenada(s.getWidth(), s.getHeight())));
    }

    private void crearNiveles(GraphicsContext bg_context) {
        Level nivel1 = new Level("/level1/bg2.png", "/level1/music.mp3", s, 2, new Coordenada(0, 0), bg_context, GameStage.probabilidades[0], 2500);
        Level nivel2 = new Level("/level2/bg2.png", "/level2/music.mp3", s, 2, new Coordenada(0, 0), bg_context, GameStage.probabilidades[0], 3500);
        Level nivel3 = new Level("/level3/bg3.png", "/level3/music.mp3", s, 2, new Coordenada(0, 0), bg_context, GameStage.probabilidades[0], 4500);
        nivel1.setP(player);
        nivel2.setP(player);
        nivel3.setP(player);
        nivel2.setTipoenemigo(FactoryEnemies.getKeyNames().get(1));
        nivel2.setTipobala(FactoryBullets.getKeyNames().get(5));
        nivel3.setTipoenemigo(FactoryEnemies.getKeyNames().get(2));
        nivel3.setTipobala(FactoryBullets.getKeyNames().get(5));
        this.niveles.add(nivel1);
        this.niveles.add(nivel2);
        this.niveles.add(nivel3);

    }

    //se inicializan la factoria de enemigos
    private void initFactory() {

    }

    private void PerderVida() {
        if (this.fighter.hascollided()) {
            this.fighter.setFree();
            this.sonidomegolpean.setVolume(150.0d);
            this.sonidomegolpean.seek(Duration.ZERO);
            this.sonidomegolpean.play();
            //this.player.setScore(this.player.getScore()+1);
            if (this.player.getLifes() > 0) {
                this.player.setLifes(this.player.getLifes() - 1);
            } else {
                this.stop();
            }
        }
    }

    @Override
    public void TicTac() {
        if (this.state == SceneState.STARTED) {
            this.actual.TicTac();
            this.fighter.TicTac();
            this.detectCollisions();

            //Comprueba si el avión ha sido golpeado y pierde una vida
            this.PerderVida();
            //para pasar de nivel
            if (this.actual.getEstado() == Level.Estado.END) {
                indicenivel++;
                if (indicenivel == this.niveles.size()) {
                    //no quedan niveles
                    this.stop();
                } else {
                    this.actual = this.niveles.get(indicenivel);
                }
            }
        }
    }

    private void detectCollisions() {
        //se mira si las balas del avión le pegan a algún enemigo
        this.fighter.getBalas().forEach(b -> {
            this.actual.getEnemigos().forEach(e -> e.isCollision(b));
        });
        this.actual.getBalasdeenemigos().forEach(be -> be.isCollision(this.fighter));
    }

    @Override
    public void onKeyPressed(KeyCode code) {
        if (this.state == SceneState.STARTED) {
            this.actual.onKeyPressed(code);
            this.fighter.onKeyPressed(code);

        }
    }

    @Override
    public void onKeyReleased(KeyCode code) {
        if (GameStage.SceneState.PRE_STARTED == this.state) {
            this.state = GameStage.SceneState.STARTED;
        }

        if (this.state == SceneState.STARTED) {
            this.actual.onKeyReleased(code);
            this.fighter.onKeyReleased(code);

        }

    }

    @Override
    public void draw(GraphicsContext gc) {
        this.actual.draw(gc);
        this.fighter.draw(gc);
        gc.setFill(Color.BLUE);
        gc.setStroke(Color.BROWN);
        gc.setFont(GameStage.font);
        gc.strokeText("SCORE " + this.player.getScore(), this.s.getWidth() / 2 - 100, 40);
        for (int i = 1; i <= this.player.getLifes(); i++) {
            gc.drawImage(imgvida, 105, 8, 50, 150,
                    80 * i, this.s.getHeight() / 1.09,
                    100, 300
            );

        }
    }

}
