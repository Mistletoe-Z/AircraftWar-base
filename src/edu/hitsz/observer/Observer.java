package edu.hitsz.observer;

public interface Observer {
    // 接收到炸弹/冰冻广播后的响应动作
    void update(String action);
}