package edu.hitsz.props;
import edu.hitsz.aircraft.AbstractAircraft;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.List;

/**
 * 所有种类道具的抽象父类
 * 出现方式：敌机坠毁后掉落
 * 移动方式：以一定速度向屏幕下方移动
 * 使用方式：与英雄机碰撞后自动触发
 * 效果
* */
public abstract class AbstractProp extends AbstractFlyingObject {

    //道具持续时间
    protected int duration;

    public AbstractProp (int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    //英雄机触碰后自动触发
    public abstract void active(HeroAircraft hero, List<AbstractAircraft> enemies);
    //道具具体加成
    public abstract void bonus(HeroAircraft hero);
    //道具出现方法在敌机中书写
}
