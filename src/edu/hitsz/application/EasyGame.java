package edu.hitsz.application;

/**
 * 简单模式具体实现类
 */
public class EasyGame extends Game {

    public EasyGame() {
        // 1. 调用父类(Game)的构造函数，传入难度标识
        // 父类接收到 "EASY" 后，会自动帮你匹配简单的背景图片 bg.jpg，并准备好对应的排行榜数据文件
        super("EASY");

        // 如果简单模式有初始属性的修改，可以写在这里
        // 例如：this.enemySpawnCycle = 20; (因为父类已经是20，这里可不写)
    }

    @Override
    protected void generateBoss() {
    }

    @Override
    protected void updateDifficulty() {
    }
}