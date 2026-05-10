package edu.hitsz.props;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.AudioManager;
import edu.hitsz.application.MusicThread;
import edu.hitsz.observer.Observer;
import edu.hitsz.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class Bombbonus extends AbstractProp implements Subject {
    private List<Observer> observerList = new ArrayList<>();
    public Bombbonus(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
        this.duration = 10000;
    }
    // --- 下面三个是实现 Subject 接口的方法 ---
    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        // 挨个通知小本本里的粉丝，触发他们的 update() 方法
        for (Observer observer : observerList) {
            observer.update("bomb");
        }
    }

    @Override
    public void bonus(HeroAircraft hero){

    }
    @Override
    public void active(HeroAircraft hero, List<AbstractAircraft> enemies) {
        // 播放爆炸音效 (根据你实验五的写法调用)
        AudioManager.getInstance().bombSound();
        System.out.println("炸弹来了!");

        // 核心：直接发广播！再也不需要在这里写 for 循环去扣敌机的血了！
        notifyObservers();
    }

}
