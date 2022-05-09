/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model.sprites;

import java.util.Iterator;
import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.Coordenada;
import pedro.ieslaencanta.com.dawairtemplate.model.Rectangle;
import pedro.ieslaencanta.com.dawairtemplate.model.Size;

/**
 *
 * @author Mateo
 */
public class Fighter extends SpriteMove implements IKeyListener, IWarnClock, ICollision {

    private boolean[] keys_presed;
    private Image img;
    private boolean colisionado;

    //path para la imagen
    private static String pathurl = "avion.png";
    //para la animación
    private int original_height;
    private LinkedList<ABullet> balas;
    private String name_bullet;

    //meto un string que tenga el nombre de los tipos de bala
    //
    /**
     *
     * @param inc incremento del movimiento
     * @param s tamaño del avión
     * @param p coordenadas iniciales
     * @param board rectangulo con las dimensiones del juego para no salirse
     */
    public Fighter(int inc, Size s, Coordenada p, Rectangle board) {
        super(inc, s, p, true, true, board);
        this.keys_presed = new boolean[5];
        this.img = new Image(getClass().getResourceAsStream("/" + Fighter.pathurl));
        //cambia al mover arriba y abajo
        this.original_height = s.getHeight();
        this.balas = new LinkedList<>();
        this.name_bullet = FactoryBullets.getKeyNames().get(0);
        this.colisionado = false;

    }

    /**
     * acciones al pulsar las teclas
     *
     * @param code
     */
    @Override
    public void onKeyPressed(KeyCode code) {

        if (code == KeyCode.RIGHT) {
            this.keys_presed[0] = true;
        }
        if (code == KeyCode.LEFT) {
            this.keys_presed[1] = true;
        }

        if (code == KeyCode.UP) {
            this.keys_presed[2] = true;
            this.getSize().setHeight(40);
        }
        if (code == KeyCode.DOWN) {
            this.keys_presed[3] = true;
            this.getSize().setHeight(40);
        }

    }

    /**
     * acciones al soltar el teclado
     *
     * @param code
     */
    @Override
    public void onKeyReleased(KeyCode code) {

        if (code == KeyCode.SPACE) {
            ABullet nueva = FactoryBullets.create(this.name_bullet);
            nueva.init(new Coordenada((this.getPosicion().getX() + this.getSize().getWidth()), this.getPosicion().getY() + this.getSize().getHeight() / 2), board);
            this.balas.add(nueva);

        }
        if (code == KeyCode.RIGHT) {
            this.keys_presed[0] = false;
        }
        if (code == KeyCode.LEFT) {
            this.keys_presed[1] = false;
        }
        if (code == KeyCode.UP) {
            this.keys_presed[2] = false;
            this.getSize().setHeight(original_height);
        }
        if (code == KeyCode.DOWN) {
            this.keys_presed[3] = false;
            this.getSize().setHeight(original_height);
        }
        if (code == KeyCode.W) {
            //System.out.println("Antes es " + this.name_bullet);
            this.name_bullet = FactoryBullets.getNext(this.name_bullet);
            //System.out.println("Despues es " + this.name_bullet);
        }
    }

    /**
     * dibujar, es algo más complejo al moverse las alas
     *
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        if (keys_presed[2]) {
            gc.drawImage(img, 163, 7, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                    this.getPosicion().getX(), this.getPosicion().getY(),
                    this.getSize().getWidth(), this.getSize().getHeight());
        } else {
            if (keys_presed[3]) {
                gc.drawImage(img, 54, 7, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                        this.getPosicion().getX(), this.getPosicion().getY(),
                        this.getSize().getWidth(), this.getSize().getHeight());
            } else {
                gc.drawImage(img, 105, 8, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                        this.getPosicion().getX(), this.getPosicion().getY(),
                        this.getSize().getWidth(), this.getSize().getHeight());
            }
        }
        ABullet tempo;
        Iterator<ABullet> iterador = this.getBalas().iterator();
        while (iterador.hasNext()) {
            tempo = iterador.next();
            tempo.draw(gc);
        }
        /*
        gc.setFill(Color.BLUEVIOLET);
        gc.fillRect(board.getEnd().getX() / 2, board.getEnd().getY() - 42, 35, 35);
        */
        ABullet balaejemplo = FactoryBullets.create(this.name_bullet);
        balaejemplo.init(new Coordenada(board.getEnd().getX() / 2, board.getEnd().getY() - 40), board);
        balaejemplo.draw(gc);
    }

//movimiento del avión
    private void move() {

        if (this.keys_presed[0]) {
            this.move(Direction.RIGHT);
        }
        if (this.keys_presed[1]) {
            this.move(Direction.LEFT);
        }
        if (this.keys_presed[2]) {
            this.move(Direction.UP);
        }
        if (this.keys_presed[3]) {
            this.move(Direction.DOWN);
        }
    }

    /**
     * cada vez que se recibe un tictac se mueve, faltan las balas del fighter
     */
    @Override
    public void TicTac() {
        this.move();
        ABullet tempo;
        Iterator<ABullet> iterador = this.getBalas().iterator();
        while (iterador.hasNext()) {
            tempo = iterador.next();
            tempo.TicTac();
        }
        //CONDICIONES PROVISIONALES
        this.getBalas().removeIf(b -> (b.getPosicion().getX() > 950 - b.getInc() || b.hascollided())
        );

    }

    /**
     * @return the balas
     */
    public LinkedList<ABullet> getBalas() {
        return balas;
    }

    /**
     * @param balas the balas to set
     */
    public void setBalas(LinkedList<ABullet> balas) {
        this.balas = balas;
    }

    @Override
    public int getX() {
        return super.getPosicion().getX();
    }

    @Override
    public int getY() {
        return super.getPosicion().getY();
    }

    @Override
    public int getHeight() {
        return super.getSize().getHeight();
    }

    @Override
    public int getWidht() {
        return super.getSize().getWidth();
    }

    @Override
    public boolean hascollided() {
        return colisionado;
    }

    @Override
    public void setColision() {
        this.colisionado = true;
    }

    @Override
    public void setFree() {
        this.colisionado = false;
    }

}
