package edu.hitsz.observer;

public interface Subject {
    // 增加观察者 (吸粉)
    void addObserver(Observer observer);

    // 移除观察者 (脱粉)
    void removeObserver(Observer observer);

    // 通知所有观察者 (发广播)
    void notifyObservers();
}