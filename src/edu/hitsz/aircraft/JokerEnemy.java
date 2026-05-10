package edu.hitsz.aircraft;

import Strategy.SansheShoot;
import Strategy.ShootStrategy;
import Strategy.StraightShoot1Line;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.props.AbstractProp;
import edu.hitsz.props.*;

import java.util.LinkedList;
import java.util.List;

/*
 * 精英敌机
 * 按照一定轨迹、频率发射子弹 O
 * 掉落道具
 * 仅仅向下方移动O
 * */
public class JokerEnemy extends AbstractAircraft{
    //子弹威力
    private int power = 10;

    public JokerEnemy(int locationX, int locationY, int speedX, int speedY, int hp){
        super(locationX, locationY, speedX, speedY, hp);
        this.shootDefault = new SansheShoot();
        this.strategy = this.shootDefault;
    }

    @Override
    public void forward(){
        locationX += speedX;
        locationY += speedY;


        int buffer = 50;

        if (locationX <= buffer) {
            // 撞到左边界：强制将速度设为正数
            speedX = Math.abs(speedX);
            locationX = buffer;
        } else if (locationX >= Main.WINDOW_WIDTH - buffer) {
            // 撞到右边界：强制将速度设为负数
            speedX = -Math.abs(speedX);
            locationX = Main.WINDOW_WIDTH - buffer;
        }
    }
    @Override
    public List<BaseBullet> shoot(){
        return strategy.shoot(this,power);
    }

    @Override
    public List<AbstractProp> dropProps(){
        List<AbstractProp> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY();
        AbstractProp prop = new PropsFactory().createProp(PropsFactory.RandomType(),this);
        res.add(prop);
        return res;
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
        } else if ("restore5s".equals(action)) {
            // 解冻口令：把纸条上的速度还回去
            this.setspeedX(this.originalSpeedX);
            this.setspeedY(this.originalSpeedY);
        }
    }
}