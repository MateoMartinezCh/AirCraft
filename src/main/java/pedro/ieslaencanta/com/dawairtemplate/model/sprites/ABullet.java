/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model.sprites;

import javafx.scene.image.Image;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.Coordenada;
import pedro.ieslaencanta.com.dawairtemplate.model.Rectangle;

/**
 *
 * @author Mateo
 */
public abstract class ABullet extends SpriteMove implements IWarnClock, ICollision {

    protected Image img;
    protected boolean colisionada;
    private Direction direccion;

    public ABullet() {
        super();
        this.colisionada = false;
        direccion=Direction.RIGHT;
    }

    public abstract void init(Coordenada c, Rectangle board);

    public void move() {
        super.move(getDireccion());
    }

    @Override
    public void TicTac() {
        this.move();
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
        return colisionada;
    }

    @Override
    public void setColision() {
        this.colisionada = true;
    }

    @Override
    public void setFree() {
        this.colisionada = false;
    }

    /**
     * @return the direccion
     */
    public Direction getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(Direction direccion) {
        this.direccion = direccion;
    }

}
