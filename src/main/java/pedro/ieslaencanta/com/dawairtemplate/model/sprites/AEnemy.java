/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model.sprites;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Supplier;
import javafx.scene.image.Image;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.Coordenada;
import pedro.ieslaencanta.com.dawairtemplate.model.Rectangle;

/**
 * Ahora mismo sin uso
 *
 * @author Mateo
 */
public abstract class AEnemy extends SpriteMove implements IShoot, IWarnClock, ICollision {

    private boolean colisionado;
    protected Image img;
    private ArrayList<ABullet> balas;
    private int contador = 0;
    private String tipobala;

    public AEnemy() {
        super();
        this.colisionado = false;
        this.tipobala=new String();
    }

    public abstract void init(Coordenada p, Rectangle board);

    //movimiento del avión enemigo
    public void move() {
        this.move(Direction.LEFT);
    }

    @Override
    public void shoot() {
        //if (Math.random() < 0.01) {
        ABullet nueva = FactoryBullets.create(tipobala);
        nueva.init(new Coordenada(this.getPosicion().getX(), this.getPosicion().getY() + this.getSize().getHeight() / 2), board);
        nueva.setDireccion(Direction.LEFT);
        this.balas.add(nueva);
        //}
    }

    @Override
    public void TicTac() {
        this.move();
        this.shootcooldown();
    }

    private void shootcooldown() {
        if (this.contador < 200) {
            this.contador=this.contador+(int)(Math.random()*6);
        } else {
            this.shoot();
            this.contador = 0;
        }
    }

    @Override
    public int getX() {
        return this.getPosicion().getX();
    }

    @Override
    public int getY() {
        return this.getPosicion().getY();
    }

    @Override
    public int getHeight() {
        return this.getSize().getHeight();
    }

    @Override
    public int getWidht() {
        return this.getSize().getWidth();
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

    /**
     * @param balas the balas to set
     */
    public void setBalas(ArrayList<ABullet> balas) {
        this.balas = balas;
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
