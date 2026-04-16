package Strategy;

import Strategy.ShootStrategy;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class CircleShoot implements ShootStrategy {
    //多发子弹横向偏移
    private int bulletshiftx = 0;
    //每次射击发射子弹数量
    private int shootNum = 11;
    //子弹标量飞行速度
    private int bulletSpeed = 5;
    //多子弹发散角,子弹越多圆心越大，方便改变弹道形状
    private double spreadAngle = 36.0;//比较密集

    public CircleShoot(){
    }

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft, int power){
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();//固定向上偏移
        int direction = (aircraft instanceof edu.hitsz.aircraft.HeroAircraft) ? -1 : 1;
        double baseAngle = (direction == -1) ? -90.0 : 90.0;


        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            //计算当前子弹的角度
            double angleOffset = (i - (shootNum - 1) / 2.0) * spreadAngle;
            double radian = Math.toRadians(baseAngle + angleOffset);
            int speedX =(int) Math.round(Math.cos(radian) * bulletSpeed);
            int speedY = aircraft.getSpeedY()+(int) Math.round(Math.sin(radian) * bulletSpeed);
            if(aircraft instanceof edu.hitsz.aircraft.HeroAircraft){
                bullet = new HeroBullet(x + (i*2 - shootNum + 1)*bulletshiftx, y, speedX, speedY, power);
            }else{
                bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*bulletshiftx, y, speedX, speedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}
