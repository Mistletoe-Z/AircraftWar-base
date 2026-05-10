package edu.hitsz.props;

import Strategy.CircleShoot;
import Strategy.SansheShoot;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.AudioManager;
import edu.hitsz.application.MusicThread;
import edu.hitsz.observer.Observer;
import edu.hitsz.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class Frozenbonus extends AbstractProp implements Subject {
    private List<Observer> observerList = new ArrayList<>();
    public Frozenbonus(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
        this.duration = 10000;//模拟永久睡眠
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
            observer.update("freeze");
        }
    }
    @Override
    public void bonus(HeroAircraft hero){

    }
    @Override
    public void active(HeroAircraft hero, List<AbstractAircraft> enemies) {
        // 1. 播放吃冰冻道具的音效（如果有的话）
        // AudioManager.getInstance().playGetSupplySound();
        System.out.println("FrozenActive!");

        // 🌟 2. 统帅发令：全体立正！(通知小本本里的粉丝冻结)
        for (Observer observer : observerList) {
            observer.update("freeze");
        }

        // 🌟 3. 掏出秒表：开启多线程准备解冻
        Runnable r = () -> {
            try {
                // 线程在这里睡上 5 秒钟 (5000毫秒)，此时游戏画面还在正常刷新，但敌机速度是0
                for (int i = 0; i <= 55; i++) {
                    Thread.sleep(1000);//1s
                    // 1 秒后醒来，统帅再次发令：全体稍息！(解冻)
                    if(i == 2){
                        for (Observer observer : observerList) {
                            observer.update("restore3s");
                        }
                    }
                    if(i == 3){
                        for (Observer observer : observerList) {
                            observer.update("restore4s");
                        }
                    }
                    if(i == 4){
                        for (Observer observer : observerList) {
                            observer.update("restore5s");
                        }
                    }
                    if(i == 50){
                        for (Observer observer : observerList) {
                            observer.update("restore50s");
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        // 启动秒表线程
        new Thread(r).start();
    }
}
