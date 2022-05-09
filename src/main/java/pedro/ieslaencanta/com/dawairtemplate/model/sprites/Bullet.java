/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model.sprites;

import java.util.Iterator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pedro.ieslaencanta.com.dawairtemplate.IWarnClock;
import pedro.ieslaencanta.com.dawairtemplate.model.Coordenada;
import pedro.ieslaencanta.com.dawairtemplate.model.Rectangle;
import pedro.ieslaencanta.com.dawairtemplate.model.Size;

/**
 *
 * @author Mateo
 */
public class Bullet extends ABullet {

    //path para la imagen
    private static String pathurl = "bullets/bullet_left.png";

    public Bullet() {
        super();
        this.img = new Image(getClass().getResourceAsStream("/" + Bullet.pathurl));

    }

    @Override
    public void init(Coordenada c, Rectangle board) {
        super.init(8, new Size(36, 8), c, true, true, board);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(img, 0, 0, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                this.getPosicion().getX(), this.getPosicion().getY(),
                this.getSize().getWidth(), this.getSize().getHeight());
    }

}
