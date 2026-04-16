package edu.hitsz.props;

import Strategy.CircleShoot;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

import java.util.List;
import java.util.List;

public class SuperFirebonus extends AbstractProp{
    public SuperFirebonus(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
        this.duration = 10000;
    }
    @Override
    public void bonus(HeroAircraft hero){
        hero.setStrategy(new CircleShoot());
    }
    @Override
    public void active(HeroAircraft hero, List<AbstractAircraft> enemies){
        System.out.println("我火力全开了！");
        //修改英雄机属性
        this.bonus(hero);

        //新线程来处理道具生命周期
        Runnable r = () -> {
            try{
                //持续时间
                Thread.sleep(duration);

                if(!hero.notValid()){
                    hero.setStrategy(hero.getShootDefault());
                    System.out.println("机体冷却中....");
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        };
        //启动这个线程
        new Thread(r).start();
    }
}