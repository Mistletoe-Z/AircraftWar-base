package edu.hitsz.aircraft;

import Strategy.CircleShoot;
import Strategy.ShootStrategy;
import Strategy.StraightShoot1Line;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.props.AbstractProp;
import edu.hitsz.props.*;
import java.util.Random;

import java.util.LinkedList;
import java.util.List;

/*
 * 精英敌机
 * 按照一定轨迹、频率发射子弹 O
 * 掉落道具
 * 仅仅向下方移动O
 * */
public class BossEnemy extends AbstractAircraft{
    //子弹威力
    private int power = 10;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp){
        super(locationX, locationY, speedX, speedY, hp);
        this.shootDefault = new CircleShoot();
        this.strategy = shootDefault;
    }

    @Override
    public void forward(){
        locationX += speedX;
        locationY += speedY;

        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT * 0.2) {
            speedY = 0;
        }

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
}
