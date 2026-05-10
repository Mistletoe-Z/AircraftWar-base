package edu.hitsz.bullet;

import edu.hitsz.observer.Observer;

/**
 * 敌机子弹
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements Observer {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }
    // 🌟 2. 加上响应广播的动作
    @Override
    public void update(String action) {
        if ("bomb".equals(action)) {
            // 炸弹口令：原地爆炸
            this.vanish();

        } else if ("freeze".equals(action)) {
            // 冰冻口令：记下当前速度，然后把速度设为 0
            this.originalSpeedX = this.getSpeedX();
            this.originalSpeedY = this.getSpeedY();
            this.setspeedX(0);
            this.setspeedY(0);

        } else if ("restore5s".equals(action)) {
            // 解冻口令：把纸条上的速度还回去
            this.setspeedX(this.originalSpeedX);
            this.setspeedY(this.originalSpeedY);
        }
    }
}
