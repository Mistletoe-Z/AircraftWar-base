package edu.hitsz.application;

import edu.hitsz.FactoryPattern.BossEnemyFactory;
import edu.hitsz.aircraft.AbstractAircraft;

/**
 * 困难模式具体实现类
 */
public class HardGame extends Game {

    private final int bossThreshold = 500;
    private int bossSpawnCount = 0;
    private final BossEnemyFactory bossFactory = new BossEnemyFactory();

    private int timeCount = 0;
    private int hpIncrease = 0;              // 小兵额外增加的血量
    private int speedYIncrease = 0;          // 小兵额外增加的速度
    private int bossHpIncrease = 0;          // 困难模式特有：Boss 额外增加的血量

    public HardGame() {
        super("HARD");
        // 困难模式开局的基准难度就偏高
        this.enemySpawnCycle = 18;
    }

    @Override
    protected void generateBoss() {
        int expectedBossCount = score / bossThreshold;
        if (expectedBossCount > bossSpawnCount) {
            bossSpawnCount++;
            AbstractAircraft boss = bossFactory.createAircraft();

            // 困难模式 Boss 血量递增机制
            int currentMaxHp = boss.getHp() + bossHpIncrease;
            boss.setHp(currentMaxHp);

            enemyAircrafts.add(boss);
            System.out.println("困难模式：高危 Boss 降临！当前血量: " + currentMaxHp);

            bossHpIncrease += 100; // 下一只 Boss 会更厚
        }
    }

    @Override
    protected void updateDifficulty() {
        timeCount++;
        // 困难模式：提升频率更快，覆盖面更广
        if (timeCount % 300 == 0) {
            // 1. 提升敌机产生周期
            if (enemySpawnCycle > 8) {
                enemySpawnCycle -= 1.0;
            }
            // 2. 【困难独有】提升英雄机与敌机射击周期（数值越小，射击越频繁）
            if (shootCycle > 5) {
                shootCycle -= 0.5;
            }
            // 3. 提升敌机血量和速度增量
            hpIncrease += 20;
            speedYIncrease += 2;

            System.out.println("困难模式难度提升：产出周期=" + enemySpawnCycle + "，射击周期=" + shootCycle + "，小兵血量+" + hpIncrease + "，速度+" + speedYIncrease);
        }
    }

    // 🌟 重写钩子方法：应用高强度的属性增强
    @Override
    protected void enhanceEnemy(AbstractAircraft enemy) {
        enemy.setHp(enemy.getHp() + hpIncrease);
        enemy.setspeedY(enemy.getSpeedY() + speedYIncrease);
    }
}