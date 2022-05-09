/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import pedro.ieslaencanta.com.dawairtemplate.model.Coordenada;
import pedro.ieslaencanta.com.dawairtemplate.model.Rectangle;
import pedro.ieslaencanta.com.dawairtemplate.model.Size;

/**
 *
 * @author Mateo
 */
public class FireBullet extends ABullet {

    //path para la imagen
    private static String pathurl = "bullets/bolafuego.png";

    public FireBullet() {
        super();
        this.img = new Image(getClass().getResourceAsStream("/" + FireBullet.pathurl));

    }

    @Override
    public void init(Coordenada c, Rectangle board) {
        super.init(8, new Size(47, 30), c, true, true, board);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(img, 0, 0, this.getSize().getWidth() , this.getSize().getHeight() ,
                this.getPosicion().getX(), this.getPosicion().getY(),
                this.getSize().getWidth(), this.getSize().getHeight());
    }

}
