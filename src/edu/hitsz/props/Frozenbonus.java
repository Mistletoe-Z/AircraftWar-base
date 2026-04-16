package edu.hitsz.props;

import Strategy.CircleShoot;
import Strategy.SansheShoot;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

import java.util.List;

public class Frozenbonus extends AbstractProp{
    public Frozenbonus(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
        this.duration = 5000;
    }
    @Override
    public void bonus(HeroAircraft hero){
        hero.setStrategy(new CircleShoot());
    }
    @Override
    public void active(HeroAircraft hero, List<AbstractAircraft> enemies){
        System.out.println("冻住，不许走！");
//        //修改敌方飞机属性
//        for (AbstractAircraft enemy : enemies) {
//            //enemy.setSpeedY(0);
//        }
//
//        //新线程来处理道具生命周期
//        Runnable r = () -> {
//            try{
//                //持续时间
//                Thread.sleep(duration);
//
//                if(!hero.notValid()){
//                    hero.repristinate();
//                    System.out.println("道具失效....");
//                }
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        };
//        //启动这个线程
//        new Thread(r).start();
    }
}
