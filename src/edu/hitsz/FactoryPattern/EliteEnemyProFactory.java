package edu.hitsz.FactoryPattern;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemyPro;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class EliteEnemyProFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createAircraft() {
        return new EliteEnemyPro(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                5,
                10,
                90
        );
    }
}
