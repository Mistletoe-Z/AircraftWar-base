package edu.hitsz.application;

import edu.hitsz.FactoryPattern.BossEnemyFactory;
import edu.hitsz.aircraft.AbstractAircraft;

/**
 * 普通模式具体实现类
 */
public class NormalGame extends Game {

    private final int bossThreshold = 500;   // 生成 Boss 的分数阈值
    private int bossSpawnCount = 0;          // 记录已经生成的 Boss 数量
    private final BossEnemyFactory bossFactory = new BossEnemyFactory();

    private int timeCount = 0;               // 用于难度递进的时间计时器
    private int hpIncrease = 0;              // 小兵额外增加的血量
    private int speedYIncrease = 0;          // 小兵额外增加的速度

    public NormalGame() {
        super("NORMAL");
    }

    @Override
    protected void generateBoss() {
        int expectedBossCount = score / bossThreshold;
        if (expectedBossCount > bossSpawnCount) {
            bossSpawnCount++;
            AbstractAircraft boss = bossFactory.createAircraft();
            enemyAircrafts.add(boss);
            System.out.println("普通模式：Boss 敌机降临！");
        }
    }

    @Override
    protected void updateDifficulty() {
        timeCount++;
        // 每过一段固定时间（假设 500 帧），提升一次小兵属性
        if (timeCount % 500 == 0) {
            // 1. 提升敌机产生周期（让飞机出得更快）
            if (enemySpawnCycle > 12) {
                enemySpawnCycle -= 0.5;
            }
            // 2. 提升敌机血量和速度增量
            hpIncrease += 10;
            speedYIncrease += 1;

            System.out.println("普通模式难度提升：产出周期缩短至 " + enemySpawnCycle + "，小兵血量增强+" + hpIncrease + "，速度增强+" + speedYIncrease);
        }
    }

    // 🌟 重写钩子方法：将累积的难度增量应用到刚出厂的敌机身上
    @Override
    protected void enhanceEnemy(AbstractAircraft enemy) {
        enemy.setHp(enemy.getHp() + hpIncrease);
        enemy.setspeedY(enemy.getSpeedY() + speedYIncrease);
    }
}