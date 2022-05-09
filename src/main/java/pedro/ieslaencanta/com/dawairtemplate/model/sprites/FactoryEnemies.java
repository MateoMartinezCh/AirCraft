/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedro.ieslaencanta.com.dawairtemplate.model.sprites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author Mateo
 */
public class FactoryEnemies {

    private static HashMap<String, Supplier<AEnemy>> enemigos;
    private static ArrayList<String> names;

    static {
        enemigos = new HashMap<>();
        names = new ArrayList<>();
    }

    public static void addEnemy(String name, Supplier<AEnemy> s) {
        FactoryEnemies.enemigos.put(name, s);
        FactoryEnemies.names.add(name);
    }

    public static AEnemy get(Supplier<? extends AEnemy> s) {
        return s.get();
    }

    public static List<String> getKeyNames() {
        return FactoryEnemies.names;
// return new ArrayList<String>(FactoryEnemigos.enemigos.keySet());
    }

    public static AEnemy create(String nombre) {
        if (FactoryEnemies.enemigos.get(nombre) != null) {
            return FactoryEnemies.enemigos.get(nombre).get();
        } else {
            return null;
        }
    }
}
