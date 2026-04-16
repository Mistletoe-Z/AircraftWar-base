package edu.hitsz.props;
import edu.hitsz.aircraft.*;

import java.util.Random;

import java.util.HashMap;
import java.util.Map;

public class PropsFactory {
    public static int PropsNum = 5;
    public static Map<Integer,String> dictionary = new HashMap<>();
    static {
        dictionary.put(0,"FirePowerbonus");
        dictionary.put(1,"SuperFirebonus");
        dictionary.put(2,"Frozenbonus");
        dictionary.put(3,"SwitchTrajbonus");
        dictionary.put(4,"Bloodbonus");
    }
    public static String RandomType(){
        Random random = new Random();
        int temp = random.nextInt(PropsNum);
        String enemyType = dictionary.get(temp);
        return enemyType;
    }

    public static AbstractProp createProp(String type,AbstractAircraft enemy){
        switch (type){
            case "FirePowerbonus":
                FirePowerbonus a = new FirePowerbonus(enemy.getLocationX(), enemy.getLocationY(),0, enemy.getSpeedY());
                return a;
            case "SuperFirebonus":
                SuperFirebonus b = new SuperFirebonus(enemy.getLocationX(), enemy.getLocationY(),0, enemy.getSpeedY());
                return b;
            case "Frozenbonus":
                Frozenbonus c = new Frozenbonus(enemy.getLocationX(), enemy.getLocationY(),0, enemy.getSpeedY());
                return c;
            case "SwitchTrajbonus":
                Bombbonus d = new Bombbonus(enemy.getLocationX(), enemy.getLocationY(),0, enemy.getSpeedY());
                return d;
            case "Bloodbonus":
                Bloodbonus e = new Bloodbonus(enemy.getLocationX(), enemy.getLocationY(),0, enemy.getSpeedY());
                return e;
            default:
                throw new IllegalArgumentException("Unknown prop type!");
        }
    }
}

