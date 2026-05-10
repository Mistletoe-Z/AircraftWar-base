package edu.hitsz.FactoryPattern;
import edu.hitsz.aircraft.AbstractAircraft;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EnemySpawner {
    public static Map<Integer, EnemyFactory> factoryDict = new HashMap<>();

    private static final int BOSS_SCORE_THRESHOLD = 500;
    private static int bossSpawnCount = 0;

    // 静态代码块：注册所有的流水线工厂
    static {
        factoryDict.put(0, new MobFactory());
        factoryDict.put(1, new EliteFactory());
        factoryDict.put(2, new EliteEnemyProFactory());
        factoryDict.put(3, new JokerEnemyFactory());
        factoryDict.put(4, new BossEnemyFactory());
    }

    public static void resetBossCount() {
        bossSpawnCount = 0;
    }

    /**
     * 直接返回生成好的敌机对象
     */
    public static AbstractAircraft generateRandomEnemy(int score) {
        int expectedBossCount = score / BOSS_SCORE_THRESHOLD;

        //该刷 Boss 了
//        if (expectedBossCount > bossSpawnCount) {
//            bossSpawnCount++;
//            return factoryDict.get(4).createAircraft();
//        }

        Random random = new Random();
        int rand = random.nextInt(100) + 1;
        int temp;

        if (rand <= 10) {
            temp = 3;
        } else if (rand <= 25) {
            temp = 2;
        } else if (rand <= 50) {
            temp = 1;
        } else {
            temp = 0;
        }

        return factoryDict.get(temp).createAircraft();
    }
}