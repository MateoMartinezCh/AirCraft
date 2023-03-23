/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pedro.ieslaencanta.com.dawairtemplate.Background;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.ABullet;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.AEnemy;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.Bullet;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.Enemy;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.Enemy2;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.Enemy3;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.EnergyBullet;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.EnergyBullet_Enemy;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.FactoryBullets;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.FactoryEnemies;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.Fighter;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.FireBullet;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IDrawable;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IKeyListener;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.LeftBullet;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.MisilBullet;

/**
 *
 * @author Pedro
 */
public class Level implements IDrawable, IWarnClock, IKeyListener {

    public enum Estado {
        PRE_STARTED,
        RUNNING,
        STOPPED,
        PAUSED,
        PRE_END,
        END
    }
    private static String[] msg = {"\"Pulsar una tecla para empezar", "Siguiente nivel..."};
    private String background_path;
    private int speed;
    private int position;
    private int fin;
    private Background background;
    private String tipoenemigo;
    private String tipobala;
    private GraphicsContext bg_ctx;
    private MediaPlayer player;
    private float[] probabilidadenemigos;
    private Size s;
    private ArrayList<AEnemy> enemigos;
    private ArrayList<ABullet> balasdeenemigos;
    private Estado estado;
    private Player p;

    public Level(String image_path, String music_path, Size s, int speed, Coordenada start_position, GraphicsContext bg_ctx, float[] probabilidad_enemigos, int fin) {
        this.InitEnemyFactory();
        this.InitBulletFactory();
        this.background = new Background(image_path, s, speed, start_position);
        this.background.setBg(bg_ctx);
        this.position = 0;
        this.speed = speed;
        this.estado = Estado.PRE_STARTED;
        this.fin = fin;
        this.s = s;
        //crear el avion
        this.probabilidadenemigos = probabilidad_enemigos;
        this.initSound(music_path);
        this.enemigos = new ArrayList<>();
        this.balasdeenemigos = new ArrayList<>();
        this.tipoenemigo=FactoryEnemies.getKeyNames().get(0);
        this.tipobala=FactoryBullets.getKeyNames().get(4);
    }

    private void InitEnemyFactory() {
        FactoryEnemies.addEnemy("Enemigo1", Enemy::new);
        FactoryEnemies.addEnemy("Enemigo2", Enemy2::new);
        FactoryEnemies.addEnemy("Enemigo3", Enemy3::new);

    }

    private void InitBulletFactory() {
        FactoryBullets.addEnemy("Bala", Bullet::new);
        FactoryBullets.addEnemy("FireBullet", FireBullet::new);
        FactoryBullets.addEnemy("EnergyBullet", EnergyBullet::new);
        FactoryBullets.addEnemy("MisilBullet", MisilBullet::new);
        FactoryBullets.addEnemy("BalaEnemigo", LeftBullet::new);
        FactoryBullets.addEnemy("EnergyBulletEnemy", EnergyBullet_Enemy::new);

    }

    private void crearEnemigos() {
        AEnemy tempo;
        float numerorandom = (float) (Math.random() * 15);
        if (numerorandom < this.probabilidadenemigos[0]) {
           
            tempo = FactoryEnemies.create(tipoenemigo);
            int cordyrandom = (int) (Math.random() * 430);
            tempo.init(new Coordenada(this.s.getWidth(), cordyrandom),
                    new Rectangle(
                            new Coordenada(this.s.getWidth(), cordyrandom),
                            new Coordenada(s.getWidth(), s.getHeight())));
            tempo.setBalas(balasdeenemigos);
            tempo.setTipobala(tipobala);
            enemigos.add(tempo);
        }

    }

    private void initSound(String music_path) {
        this.player = new MediaPlayer(new Media(getClass().getResource(music_path).toString()));

        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.seek(Duration.ZERO);
            }
        });

    }

    @Override
    public void draw(GraphicsContext gc) {
        this.background.paint(gc);

        AEnemy tempo;
        Iterator<AEnemy> iterador = this.enemigos.iterator();
        if (!this.enemigos.isEmpty()) {
            while (iterador.hasNext()) {
                tempo = iterador.next();
                tempo.draw(gc);
            }
        }
        if (this.estado == Estado.PRE_STARTED) {
            gc.setFill(Color.BROWN);
            gc.setStroke(Color.WHITE);
            gc.strokeText(Level.msg[0], 100, 200);
            gc.fillText(Level.msg[0], 100, 200);

        }
        ABullet tempobala;
        Iterator<ABullet> iteradorbalas = this.balasdeenemigos.iterator();
        while (iteradorbalas.hasNext()) {
            tempobala = iteradorbalas.next();
            tempobala.draw(gc);
        }

    }

    @Override
    public void TicTac() {
        if (this.getEstado() == Estado.RUNNING) {
            //llamar a tictac de los hijos
            this.TicTacChildrens();
            this.crearEnemigos();
            this.moveBullets();

            //posicion en la que termina
            if (this.position < this.fin) {
                this.position += this.speed;
            }
            else {
                this.EndLevel();
            }
        }
    }

   

    private void TicTacChildrens() {
        //pintar el fondo
        this.background.TicTac();
        this.enemigos.forEach(e ->{
            if (e.hascollided()) {
                this.p.setScore(this.p.getScore()+5);
            }
        });
        AEnemy tempo;
        Iterator<AEnemy> iterador = this.enemigos.iterator();
        if (!this.enemigos.isEmpty()) {

            while (iterador.hasNext()) {
                tempo = iterador.next();
                tempo.TicTac();
            }
            this.enemigos.removeIf(e -> (e.getPosicion().getX() <= e.getInc() || !e.isLive() || e.hascollided()));
        }
    }

    private void moveBullets() {
        //mover las balas
        //comprobar las condiciones para borrar y borrar las balas
        ABullet tempobala;
        Iterator<ABullet> iteradorbalas = this.balasdeenemigos.iterator();
        while (iteradorbalas.hasNext()) {
            tempobala = iteradorbalas.next();
            tempobala.TicTac();
        }
        this.balasdeenemigos.removeIf(e -> (e.getPosicion().getX() <= e.getInc() || !e.isLive() || e.hascollided()));

    }

    public boolean isEnd() {
        return this.getEstado() == Estado.STOPPED;
    }

    private void EndLevel() {
        this.player.stop();
        this.setEstado(Estado.END);
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @param p the p to set
     */
    public void setP(Player p) {
        this.p = p;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public void onKeyPressed(KeyCode code) {
        //pasar el key code al avion
        if (this.getEstado() == Level.Estado.RUNNING) {
        }
    }

    @Override
    public void onKeyReleased(KeyCode code) {
        //para iniciar el juego
        if (this.getEstado() == Level.Estado.PRE_STARTED) {
            this.setEstado(Level.Estado.RUNNING);
        }
        if (this.getEstado() == Level.Estado.RUNNING) {
            if (player.getStatus() == MediaPlayer.Status.READY) {
                player.play();
            }
        }

    }

    /**
     * @return the enemigos
     */
    public ArrayList<AEnemy> getEnemigos() {
        return enemigos;
    }

    /**
     * @return the balasdeenemigos
     */
    public ArrayList<ABullet> getBalasdeenemigos() {
        return balasdeenemigos;
    }

    /**
     * @return the tipoenemigo
     */
    public String getTipoenemigo() {
        return tipoenemigo;
    }

    /**
     * @param tipoenemigo the tipoenemigo to set
     */
    public void setTipoenemigo(String tipoenemigo) {
        this.tipoenemigo = tipoenemigo;
    }

    /**
     * @return the tipobala
     */
    public String getTipobala() {
        return tipobala;
    }

    /**
     * @param tipobala the tipobala to set
     */
    public void setTipobala(String tipobala) {
        this.tipobala = tipobala;
    }
}
