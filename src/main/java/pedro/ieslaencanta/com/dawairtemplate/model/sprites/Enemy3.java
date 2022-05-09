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
public class Enemy3 extends AEnemy{
    //path para la imagen
    private static String pathurl = "enemigos/e3.png";

    public Enemy3() {
        super();
        this.img = new Image(getClass().getResourceAsStream("/" + Enemy3.pathurl));

    }

    @Override
    public void init(Coordenada p, Rectangle board) {
        super.init(3, new Size(250, 52), p, true, true, board);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(img, 387, 0, this.getSize().getWidth() / 2, this.getSize().getHeight() / 2,
                this.getPosicion().getX(), this.getPosicion().getY(),
                this.getSize().getWidth(), this.getSize().getHeight());
    }
   

}
