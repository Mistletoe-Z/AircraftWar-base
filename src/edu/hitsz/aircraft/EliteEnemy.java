package edu.hitsz.aircraft;

import Strategy.ShootStrategy;
import Strategy.StraightShoot1Line;
import Strategy.StraightShoot2Line;
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
public class EliteEnemy extends AbstractAircraft{
    //子弹威力
    private int power = 10;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp){
        super(locationX, locationY, speedX, speedY, hp);
        this.shootDefault = new StraightShoot1Line();
        this.strategy = this.shootDefault;
    }

    @Override
    public void forward(){
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
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
