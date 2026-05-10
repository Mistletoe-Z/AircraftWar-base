package edu.hitsz.aircraft;

import Strategy.ShootStrategy;
import Strategy.StraightShoot1Line;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击、不掉落道具
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);

    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }
    @Override
    public void update(String action) {
        if ("bomb".equals(action)) {
            // 炸弹口令：原地爆炸
            this.decreaseHp(Integer.MAX_VALUE);

        } else if ("freeze".equals(action)) {
            // 冰冻口令：记下当前速度，然后把速度设为 0
            this.originalSpeedX = this.getSpeedX();
            this.originalSpeedY = this.getSpeedY();
            this.setspeedX(0);
            this.setspeedY(0);
        } else if ("restore50s".equals(action)) {
            // 解冻口令：把纸条上的速度还回去
            this.setspeedX(this.originalSpeedX);
            this.setspeedY(this.originalSpeedY);
        }
    }
}
