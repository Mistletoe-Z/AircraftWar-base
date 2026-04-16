package edu.hitsz.props;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

import java.util.List;
import edu.hitsz.aircraft.HeroAircraft;

public class Bloodbonus extends AbstractProp{
    public Bloodbonus(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
        this.duration = 10;
    }
    @Override
    public void bonus(HeroAircraft hero){
        hero.UpHp(20);
    }
    @Override
    public void active(HeroAircraft hero,List<AbstractAircraft> enemies){
        System.out.println("邪气退散！");
        //修改英雄机属性
        this.bonus(hero);
    }
}
