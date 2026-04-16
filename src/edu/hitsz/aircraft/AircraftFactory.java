package edu.hitsz.aircraft;
import edu.hitsz.aircraft.*;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import java.util.Random;

import java.util.HashMap;
import java.util.Map;

public class AircraftFactory {
    public static Map<Integer,String> dictionary = new HashMap<>();
    static {
        dictionary.put(0,"MobEnemy");
        dictionary.put(1,"EliteEnemy");
        dictionary.put(2,"EliteEnemyPro");
        dictionary.put(3,"JokerEnemy");
        dictionary.put(4,"BossEnemy");
    }
    private static final int BOSS_THRESHOLD = 200;
    private static int bossCount = 0;
    public static String RandomType(int score){
        int delta = score-bossCount;
        if (delta >= BOSS_THRESHOLD){
            bossCount = score;
            return dictionary.get(4);
        }

        Random random = new Random();
        int rand = random.nextInt(100)+1;
        int temp;
        if (rand<=10) {
            temp =3;
        } else if (rand<=25) {
            temp =2;
        } else if (rand<=50) {
            temp =1;
        }else {
            temp =0;
        }
        String enemyType = dictionary.get(temp);
        return enemyType;
    }

    public static AbstractAircraft createAircraft(String type){
        switch (type){
            case "MobEnemy":
                MobEnemy a = new MobEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                        0,
                        10,
                        30
                );
                return a;

            case "EliteEnemy":
                EliteEnemy b = new EliteEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                        0,
                        10,
                        60
                );
                return b;

            case "EliteEnemyPro":
                EliteEnemyPro c = new EliteEnemyPro(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                        5,
                        10,
                        90
                );
                return c;

            case "JokerEnemy":
                JokerEnemy d = new JokerEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                        3,
                        10,
                        120
                );
                return d;

            case "BossEnemy":
                BossEnemy e = new BossEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                        2,
                        10,
                        200
                );
                return e;

            default:
                throw new IllegalArgumentException("Unknown aircraft type!");
        }
    }
}
