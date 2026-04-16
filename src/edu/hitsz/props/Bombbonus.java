package edu.hitsz.props;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

import java.util.List;

public class Bombbonus extends AbstractProp{
    public Bombbonus(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
        this.duration = 10000;
    }
    @Override
    public void bonus(HeroAircraft hero){

    }
    @Override
    public void active(HeroAircraft hero, List<AbstractAircraft> enemies){
        System.out.println("炸弹来了！");
//        //修改英雄机属性
//        this.bonus(hero);
//
//        //新线程来处理道具生命周期
//        Runnable r = () -> {
//            try{
//                //持续时间
//                Thread.sleep(duration);
//
//                if(!hero.notValid()){
//                    hero.repristinate();
//                    System.out.println("弹道已恢复正常....");
//                }
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        };
//        //启动这个线程
//        new Thread(r).start();
    }
}
