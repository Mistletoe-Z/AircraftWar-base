package edu.hitsz.props;
import Strategy.SansheShoot;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

import java.util.List;
import edu.hitsz.aircraft.HeroAircraft;

public class FirePowerbonus extends AbstractProp{
    public FirePowerbonus(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
        this.duration = 10000;
    }
    @Override
    public void bonus(HeroAircraft hero){
        hero.setStrategy(new SansheShoot());
    }
    @Override
    public void active(HeroAircraft hero,List<AbstractAircraft> enemies){
        System.out.println("火力加强！");
        //修改英雄机属性
        this.bonus(hero);

        //新线程来处理道具生命周期
        Runnable r = () -> {
            try{
                //持续时间
                Thread.sleep(duration);

                if(!hero.notValid()){
                    hero.setStrategy(hero.getShootDefault());
                    System.out.println("火力恢复正常");
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        };
        //启动这个线程
        new Thread(r).start();
    }
}
