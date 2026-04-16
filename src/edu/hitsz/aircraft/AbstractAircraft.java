package edu.hitsz.aircraft;

import Strategy.ShootStrategy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.props.AbstractProp;
import edu.hitsz.props.PropsFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * 所有种类飞机的抽象父类
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    //最大生命值
    protected int maxHp;
    protected int hp;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }



    /**
     * 飞机射击方法
     * @return
     *  可射击对象需实现，返回子弹列表
     *  非可射击对象空实现，返回空列表
     */
    public abstract List<BaseBullet> shoot();

    /**
     * 敌机掉落道具的方法
     * @return 掉落道具列表。
     * 默认不掉落
     * **/
    public List<AbstractProp> dropProps(){
        List<AbstractProp> res = new LinkedList<>();
        return res;
    }
    /**
     * 武器设计方式管理
     *
     * **/
    protected ShootStrategy strategy;
    protected ShootStrategy shootDefault;
    public ShootStrategy getShootDefault(){
        return this.shootDefault;
    }
    public void setStrategy(ShootStrategy strategy){
        this.strategy = strategy;
    }
}


