package edu.hitsz.FactoryPattern;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.JokerEnemy;
import edu.hitsz.application.AudioManager;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class BossEnemyFactory implements EnemyFactory{
    @Override
    public AbstractAircraft createAircraft() {
        AudioManager.getInstance().playBossBGM();
        return new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                2,
                10,
                200
        );
    }
}
