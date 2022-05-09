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
public class FactoryBullets {

    private static HashMap<String, Supplier<ABullet>> balas;
    private static ArrayList<String> names;

    static {
        balas = new HashMap<>();
        names = new ArrayList<>();
    }

    public static void addEnemy(String name, Supplier<ABullet> s) {
        FactoryBullets.balas.put(name, s);
        FactoryBullets.names.add(name);
    }

    public static ABullet get(Supplier<? extends ABullet> s) {
        return s.get();
    }

    public static List<String> getKeyNames() {
        return FactoryBullets.names;
// return new ArrayList<String>(FactoryEnemigos.enemigos.keySet());
    }

    public static ABullet create(String nombre) {
        if (FactoryBullets.balas.get(nombre) != null) {
            return FactoryBullets.balas.get(nombre).get();
        } else {
            return null;
        }
    }
    public static String getNext(String actual){
        int indice=names.lastIndexOf(actual)+1;
        if(indice==names.size()-2)
            indice=0;
        return names.get(indice);
    }
}
