/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pedro.ieslaencanta.com.dawairtemplate;




import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import pedro.ieslaencanta.com.dawairtemplate.model.Size;
import pedro.ieslaencanta.com.dawairtemplate.model.sprites.IKeyListener;
import pedro.ieslaencanta.com.dawairtemplate.stages.AbstractScene;
import pedro.ieslaencanta.com.dawairtemplate.stages.GameStage;
import pedro.ieslaencanta.com.dawairtemplate.stages.IScene;


/**
 *
 * @author Pedro
 */
public class Game implements IWarnClock, IKeyListener {

    
    public static Clock clock = new Clock(40);
    private AbstractScene escena_actual;
    private GraphicsContext ctx;


    public Game(GraphicsContext context, GraphicsContext bg_context, Size s) {
        Game.clock.addIWarnClock(this);
        this.escena_actual=new GameStage(context,bg_context,s);
        this.ctx=context;
        
    }
    public void start() {
        this.clock.start();
    }
    public void stop() {
        this.clock.stop();
    }


    @Override
    public synchronized void TicTac() {
        this.escena_actual.TicTac();
        this.escena_actual.draw(ctx);
        if (this.escena_actual.getState()==IScene.SceneState.END) {
            System.out.println("fin del juego");
        }
    }

    @Override
    public void onKeyPressed(KeyCode code) {
            this.escena_actual.onKeyPressed(code);

    }

    @Override
    public void onKeyReleased(KeyCode code) {
       this.escena_actual.onKeyReleased(code);
     }

}
